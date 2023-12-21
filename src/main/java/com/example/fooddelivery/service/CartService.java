package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.email.DeliveryGuyCartListener;
import com.example.fooddelivery.email.OnCartDeliveryGuy;
import com.example.fooddelivery.email.OnRegistrationCompleteEventCustomer;
import com.example.fooddelivery.error.NotDeletedException;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class CartService {

    @Autowired
    private CartRepository repo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private PizzaRepository pizzaRepo;
    @Autowired
    private PastaRepository pastaRepo;
    @Autowired
    private SaladRepository saladRepo;
    @Autowired
    private SteakRepository steakRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private DeliveryGuyRepository deliveryGuyRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;


    public Cart save(Cart cart){
        return repo.save(cart);
    }
    public Cart findById(String cartId){
        return repo.findById(UUID.fromString(cartId)).orElse(null);
    }
    public void removeCart(Cart cart) {
        if (cart != null) {
            // Remove associations with other entities (if needed)
            cart.setDeliveryGuy(null); // Assuming there is a reference from Cart to DeliveryGuy

            // Save changes to remove associations
            repo.save(cart);

        }
    }
    public List<String>pizzasName(List<Pizza>pizzas){
        List<String> names = new ArrayList<>();
        for(Pizza pizza:pizzas) {
            names.add(pizza.getName());
        }
        return names;
    }
    public List<String>pastasName(List<Pasta>pastas){
        List<String> names = new ArrayList<>();
        for(Pasta pasta:pastas) {
            names.add(pasta.getName());
        }
        return names;
    }
    public List<String>steaksName(List<Steak>steaks){
        List<String> names = new ArrayList<>();
        for(Steak steak:steaks) {
            names.add(steak.getName());
        }
        return names;
    }
    public List<String>saladName(List<Salad>salads){
        List<String> names = new ArrayList<>();
        for(Salad salad:salads) {
            names.add(salad.getName());
        }
        return names;
    }
    public CartResponse seeCart(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        Cart cart = authenticatedCustomer.getCart();
        CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
        cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

        return cartResponse;
    }
    public String finish(Authentication authentication, HttpServletRequest request){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        Cart cart = cartRepo.findByCustomersId(authenticatedCustomer.getId());
        DeliveryGuy deliveryGuy = cart.getDeliveryGuy();

        if(authenticatedCustomer.getCart() != null){
            LocalTime time = LocalTime.now().plusMinutes(45);
            cart.setTimeOfDelivery(time);
            repo.save(cart);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnCartDeliveryGuy(deliveryGuy, request.getLocale(), appUrl));

            authenticatedCustomer.setCart(null);
            cart.setCustomer(null);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);

        }

        return "The purchase is finished!";

    }
    @Transactional
    public CartResponse choosePizza(Authentication authentication, String pizzaName,String restaurantName){
        Pizza pizza = pizzaRepo.findByName(pizzaName);
        List<DeliveryGuy> deliveryGuys = deliveryGuyRepo.findByRestaurantName(restaurantName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizza);
        if(authenticatedCustomer.getCart() == null ){

            List<Cart>carts = new ArrayList<>();
            Cart cart =Cart.builder()
                    .id(UUID.randomUUID())
                    .createTime(LocalTime.now())
                    .customer(authenticatedCustomer)
                    .pizzas(pizzas)
                    .location(authenticatedCustomer.getAddress())
                    .fullPrice(pizza.getPrice())
                    .build();

            DeliveryGuy deliveryGuy = findAvailableDeliveryGuy(deliveryGuys);


            if (deliveryGuy != null) {
                cart.setDeliveryGuy(deliveryGuy);
                if(deliveryGuy.getCarts().size() == 5) {
                    deliveryGuy.setAvailable(false);
                }

            }

            repo.save(cart);

            if (cart.getDeliveryGuy() != null) {
                deliveryGuyRepo.save(cart.getDeliveryGuy());
            }


            pizza.setCarts(carts);

            customerRepo.save(authenticatedCustomer);


            pizzaRepo.save(pizza);

            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;

        }else {
            List<Cart>carts = new ArrayList<>();
            Cart cart =authenticatedCustomer.getCart();
            carts.add(cart);
            cart.setPizzas(pizzas);
            cart.setFullPrice(cart.getFullPrice() + pizza.getPrice());
            pizza.setCarts(carts);
            repo.save(cart);
            pizzaRepo.save(pizza);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;
        }
    }


    @Transactional
    public CartResponse choosePasta(Authentication authentication, String pastaName,String restaurantName){
        Pasta pasta = pastaRepo.findByName(pastaName);
        List<DeliveryGuy> deliveryGuys = deliveryGuyRepo.findByRestaurantName(restaurantName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        List<Pasta> pastas = new ArrayList<>();
        pastas.add(pasta);
        if(authenticatedCustomer.getCart() == null ){

            List<Cart>carts = new ArrayList<>();
            Cart cart =Cart.builder()
                    .id(UUID.randomUUID())
                    .createTime(LocalTime.now())
                    .customer(authenticatedCustomer)
                    .pastas(pastas)
                    .location(authenticatedCustomer.getAddress())
                    .fullPrice(pasta.getPrice())
                    .build();

            DeliveryGuy deliveryGuy = findAvailableDeliveryGuy(deliveryGuys);


            if (deliveryGuy != null) {
                cart.setDeliveryGuy(deliveryGuy);
                deliveryGuy.setAvailable(false);
            }

            repo.save(cart);

            if (cart.getDeliveryGuy() != null) {
                deliveryGuyRepo.save(cart.getDeliveryGuy());
            }
//
            pasta.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);

            pastaRepo.save(pasta);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;

        }else {
            List<Cart>carts = new ArrayList<>();
            Cart cart =authenticatedCustomer.getCart();
            carts.add(cart);
            cart.setPastas(pastas);
            cart.setFullPrice(cart.getFullPrice() + pasta.getPrice());
            pasta.setCarts(carts);
            repo.save(cart);
            pastaRepo.save(pasta);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;
        }
    }
    @Transactional
    public CartResponse chooseSteak(Authentication authentication, String steakName,String restaurantName){
        Steak steak = steakRepo.findByName(steakName);
        List<DeliveryGuy> deliveryGuys = deliveryGuyRepo.findByRestaurantName(restaurantName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        List<Steak>steaks = new ArrayList<>();
        steaks.add(steak);

        if(authenticatedCustomer.getCart() == null ){
            List<Cart>carts = new ArrayList<>();
            Cart cart =Cart.builder()
                    .id(UUID.randomUUID())
                    .createTime(LocalTime.now())
                    .customer(authenticatedCustomer)
                    .steaks(steaks)
                    .location(authenticatedCustomer.getAddress())
                    .fullPrice(steak.getPrice())
                    .build();
            carts.add(cart);
            for(DeliveryGuy deliveryGuy:deliveryGuys){
                if(deliveryGuy.isAvailable()){
                    cart.setDeliveryGuy(deliveryGuy);
                    deliveryGuy.setAvailable(false);

                    deliveryGuy.setCarts(carts);
                    deliveryGuyRepo.save(deliveryGuy);

                    repo.save(cart);

                }
                if(cart.getDeliveryGuy() != null){
                    break;
                }

            }

            steak.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);
            steakRepo.save(steak);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");


            return cartResponse;

        }else {
            List<Cart>carts = new ArrayList<>();
            Cart cart =authenticatedCustomer.getCart();
            carts.add(cart);
            cart.setSteaks(steaks);
            cart.setFullPrice(cart.getFullPrice() + steak.getPrice());
            steak.setCarts(carts);
            repo.save(cart);
            steakRepo.save(steak);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;
        }
    }
    @Transactional
    public CartResponse chooseSalad(Authentication authentication, String saladName,String restaurantName){
        Salad salad = saladRepo.findByName(saladName);
        List<DeliveryGuy> deliveryGuys = deliveryGuyRepo.findByRestaurantName(restaurantName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        List<Salad>salads = new ArrayList<>();
        salads.add(salad);

        if(authenticatedCustomer.getCart() == null ){
            List<Cart>carts = new ArrayList<>();
            Cart cart =Cart.builder()
                    .id(UUID.randomUUID())
                    .createTime(LocalTime.now())
                    .customer(authenticatedCustomer)
                    .salads(salads)
                    .location(authenticatedCustomer.getAddress())
                    .fullPrice(salad.getPrice())
                    .build();
            carts.add(cart);
            for(DeliveryGuy deliveryGuy:deliveryGuys){
                if(deliveryGuy.isAvailable()){
                    cart.setDeliveryGuy(deliveryGuy);
                    deliveryGuy.setAvailable(false);

                    deliveryGuy.setCarts(carts);
                    deliveryGuyRepo.save(deliveryGuy);

                    repo.save(cart);

                }
                if(cart.getDeliveryGuy() != null){
                    break;
                }

            }

            carts.add(cart);
            salad.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);
            saladRepo.save(salad);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;

        }else {
            List<Cart>carts = new ArrayList<>();
            Cart cart =authenticatedCustomer.getCart();
            carts.add(cart);
            cart.setSalads(salads);
            cart.setFullPrice(cart.getFullPrice() + salad.getPrice());
            salad.setCarts(carts);
            repo.save(cart);
            saladRepo.save(salad);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
            cartResponse.setFinish("http://localhost:8084/delivery/cart/finish");

            return cartResponse;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public String deleteCart(Authentication authentication) throws NotDeletedException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        repo.deleteByCustomerUsername(authenticatedCustomer.getUsername());
        Cart cart = repo.findByCustomersUsername(authenticatedCustomer.getUsername());

        if(cart != null){
            throw new NotDeletedException("It is not deleted!");
        }
        return "It is deleted successfully!";
    }
    private DeliveryGuy findAvailableDeliveryGuy(List<DeliveryGuy> deliveryGuys) {
        for (DeliveryGuy deliveryGuy : deliveryGuys) {
            if (deliveryGuy.isAvailable()) {
                return deliveryGuy;
            }
        }
        return null;
    }
}

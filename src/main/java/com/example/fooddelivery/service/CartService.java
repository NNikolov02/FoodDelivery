package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
    private CartMapper cartMapper;
    @Autowired
    private DeliveryGuyRepository deliveryGuyRepo;


    public Cart save(Cart cart){
        return repo.save(cart);
    }
    public CartResponse seeCart(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        Cart cart = authenticatedCustomer.getCart();
        CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

        return cartResponse;
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

                for(DeliveryGuy deliveryGuy:deliveryGuys){
                    if(deliveryGuy.isAvailable()){
                        cart.setDeliveryGuy(deliveryGuy);
                        deliveryGuy.setAvailable(false);

                        deliveryGuy.setCart(cart);
                        deliveryGuyRepo.save(deliveryGuy);

                        repo.save(cart);

                    }
                    if(cart.getDeliveryGuy() != null){
                        break;
                    }

                }

            carts.add(cart);
            pizza.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);

            pizzaRepo.save(pizza);

            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

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

            return cartResponse;
        }
    }
    @Transactional
    public CartResponse choosePasta(Authentication authentication, String pastaName){
        Pasta pasta = pastaRepo.findByName(pastaName);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Customer authenticatedCustomer = customerRepo.findCustomerByUsername(userDetails.getUsername());
        List<Pasta>pastas = new ArrayList<>();
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
            carts.add(cart);
            pasta.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);
            pastaRepo.save(pasta);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

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

            return cartResponse;
        }
    }
    @Transactional
    public CartResponse chooseSteak(Authentication authentication, String steakName){
        Steak steak = steakRepo.findByName(steakName);
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
            steak.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);
            steakRepo.save(steak);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

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

            return cartResponse;
        }
    }
    @Transactional
    public CartResponse chooseSalad(Authentication authentication, String saladName){
        Salad salad = saladRepo.findByName(saladName);
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
            salad.setCarts(carts);
            authenticatedCustomer.setCart(cart);
            customerRepo.save(authenticatedCustomer);
            repo.save(cart);
            saladRepo.save(salad);


            CartResponse cartResponse = cartMapper.responseFromModelOne(cart);

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

            return cartResponse;
        }
    }
}

package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.cart.CartDeliveryGuy;
import com.example.fooddelivery.dto.cart.CartDto;
import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.deliveryguy.DeliverGuyResponsePrivate;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyCreateRequest;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyUpdateRequest;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.DeliveryGuyMapper;
import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.DeliveryGuyPagingRepository;
import com.example.fooddelivery.repository.DeliveryGuyRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Service
public class DeliveryGuyService {

    @Autowired
    private DeliveryGuyRepository repo;
    @Autowired
    private DeliveryGuyMapper deliveryGuyMapper;

    @Autowired
    private ObjectValidator validator;
    @Autowired
    private DeliveryGuyPagingRepository pagingRepo;
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private CartService cartService;
    public DeliveryGuy save(DeliveryGuy deliveryGuy){
        return repo.save(deliveryGuy);
    }

    public Page<DeliveryGuy> fetchAll(int currentPage, int pageSize) {
        return pagingRepo.findAll(PageRequest.of(currentPage, pageSize));
    }
    public DeliveryGuy findByName(String name){
        return repo.findByUsername(name);
    }
    @Transactional
    public void deleteByName(String name){
        repo.deleteByUsername(name);
    }
    public List<DeliveryGuy>findByAvailable(boolean available){
        return repo.findByAvailable(available);
    }
    public List<DeliveryGuy>findByRestaurant(String name){
        return repo.findByRestaurantName(name);
    }
//    public DeliveryGuy findByCartId(String id){
//        return repo.findByCartId(UUID.fromString(id)).getCart().getDeliveryGuy();
//    }
    public DeliverGuyResponsePrivate seeCredentials(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());

        DeliverGuyResponsePrivate deliverGuyResponsePrivate = deliveryGuyMapper.responseFromModel(authenticatedDeliveryGuy);

        return deliverGuyResponsePrivate;
    }
    public List<CartDeliveryGuy>viewCarts(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());
        List<Cart>carts = authenticatedDeliveryGuy.getCarts();

        List<CartDeliveryGuy> cartsResponse = deliveryGuyMapper.responseFromCart(carts);
        for(CartDeliveryGuy cartDeliveryGuy:cartsResponse){
            cartDeliveryGuy.setIsDelivered("http://localhost:8084/delivery/deliveryGuy/" + cartDeliveryGuy.getId() + "/delivered"+ "?delivered=true");
        }

        return cartsResponse;
    }
    public DeliveryGuyResponse create(DeliveryGuyCreateRequest deliveryGuyDto,String restaurantName){
        Map<String, String> validationErrors = validator.validate(deliveryGuyDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid DeliveryGuy Create", validationErrors);
        }
        List<DeliveryGuy>deliveryGuys = new ArrayList<>();
        Restaurant restaurant = restaurantRepo.findByName(restaurantName);
        DeliveryGuy create = deliveryGuyMapper.modelFromCreateRequest(deliveryGuyDto);
        create.setRestaurant(restaurant);
        DeliveryGuy deliveryGuy = repo.save(create);
        deliveryGuys.add(deliveryGuy);
        restaurant.setDeliveryGuys(deliveryGuys);
        restaurantRepo.save(restaurant);
        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyMapper.responseFromModelOne(deliveryGuy);

        return deliveryGuyResponse;

    }
    @Transactional
    public String deliveredCart(Authentication authentication, boolean delivered, String cartId) {
        Cart cart = cartService.findById(cartId);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());

        if (delivered) {
            if (authenticatedDeliveryGuy.getCarts().contains(cart)) {
                authenticatedDeliveryGuy.getCarts().remove(cart);
                cartService.removeCart(cart);
            }

            if (authenticatedDeliveryGuy.getCarts().size() < 5) {
                authenticatedDeliveryGuy.setAvailable(true);
            } else {
                authenticatedDeliveryGuy.setAvailable(false);
            }

            repo.save(authenticatedDeliveryGuy);
        }

        return "I have already delivered it!";
    }

    @Transactional
    public DeliveryGuyResponse update(boolean available, Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DeliveryGuy authenticatedDeliveryGuy = repo.findByUsername(userDetails.getUsername());
        deliveryGuyMapper.updateModelFromDto(available,authenticatedDeliveryGuy);
        DeliveryGuy deliveryGuy = repo.save(authenticatedDeliveryGuy);

        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyMapper.responseFromModelOne(deliveryGuy);

        return deliveryGuyResponse;

    }

}

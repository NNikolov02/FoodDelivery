package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.cart.CartCreateRequest;
import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerResponse;
import com.example.fooddelivery.dto.pasta.PastaCreateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.CartMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.repository.PastaRepository;
import com.example.fooddelivery.repository.PizzaRepository;
import com.example.fooddelivery.service.CartService;
import com.example.fooddelivery.service.PizzaService;
import com.example.fooddelivery.validation.ObjectValidator;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/delivery/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ObjectValidator validator;

//    @PostMapping(value = "")
//    public ResponseEntity<CartResponse> createCart(@RequestBody CartCreateRequest cartDto){
//        Map<String, String> validationErrors = validator.validate(cartDto);
//        if (validationErrors.size() != 0) {
//            throw new InvalidObjectException("Invalid Cart Create", validationErrors);
//        }
//        Cart cart = cartMapper.modelFromCreateRequest(cartDto);
//        cart.setCreateTime(LocalTime.now());
//        cartService.save(cart);
//        CartResponse cartResponse = cartMapper.responseFromModelOne(cart);
//
//        return ResponseEntity.ok().body(cartResponse);
//
//
//    }

    @PostMapping (value = "/{restaurantName}/pizza")
    public ResponseEntity<CartResponse>choosePizza(@RequestParam String pizzaName,@PathVariable String restaurantName, Authentication authentication ){
        CartResponse cartResponse = cartService.choosePizza(authentication,pizzaName,restaurantName);

        return ResponseEntity.ok().body(cartResponse);

    }
    @GetMapping(value = "")
    public ResponseEntity<CartResponse>seeCart(Authentication authentication){
        CartResponse cartResponse = cartService.seeCart(authentication);

        return ResponseEntity.ok().body(cartResponse);
    }
    @PostMapping(value = "/finish")
    public ResponseEntity<String>finishPurchase(Authentication authentication, HttpServletRequest request){
        String finish = cartService.finish(authentication,request);

        return ResponseEntity.ok(finish);
    }
    @DeleteMapping(value = "")
    public ResponseEntity<String>deleteCart(Authentication authentication){
        String deletedCart = cartService.deleteCart(authentication);

        return ResponseEntity.ok(deletedCart);
    }
    @PostMapping (value = "/{restaurantName}/pasta")
    public ResponseEntity<CartResponse>choosePasta(@RequestParam String pastaName,@PathVariable String restaurantName, Authentication authentication ){
        CartResponse cartResponse = cartService.choosePasta(authentication,pastaName,restaurantName);

        return ResponseEntity.ok().body(cartResponse);
    }
    @PostMapping (value = "/{restaurantName}/steak")
    public ResponseEntity<CartResponse>chooseSteak(@RequestParam String steakName,@PathVariable String restaurantName, Authentication authentication ){
        CartResponse cartResponse = cartService.chooseSteak(authentication,steakName,restaurantName);

        return ResponseEntity.ok().body(cartResponse);
    }
    @PostMapping (value = "/{restaurantName}/salad")
    public ResponseEntity<CartResponse>chooseSalad(@RequestParam String saladName,@PathVariable String restaurantName, Authentication authentication ){
        CartResponse cartResponse = cartService.chooseSalad(authentication,saladName,restaurantName);

        return ResponseEntity.ok().body(cartResponse);
    }
}
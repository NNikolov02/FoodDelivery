package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.cart.CartDeliveryGuy;
import com.example.fooddelivery.dto.cart.CartDto;
import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.deliveryguy.DeliverGuyResponsePrivate;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyApiPage;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyCreateRequest;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantApiPage;
import com.example.fooddelivery.dto.restaurant.RestaurantResponse;
import com.example.fooddelivery.mapping.DeliveryGuyMapper;
import com.example.fooddelivery.service.DeliveryGuyService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery/deliveryGuy")
public class DeliveryGuyController {

    @Autowired
    private DeliveryGuyService deliveryGuyService;
    @Autowired
    private DeliveryGuyMapper deliveryGuyMapper;

    @GetMapping(value = "/all", produces = "application/json")
    public DeliveryGuyApiPage<DeliveryGuyResponse> getAllDeliveryGuys(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {

        Page<DeliveryGuyResponse> deliveryGuyPage = deliveryGuyService.fetchAll(currPage - 1, 10).map(deliveryGuyMapper::responseFromModelOne);

        return new DeliveryGuyApiPage<>(deliveryGuyPage);
    }
    @GetMapping("/seeProfile")
    public ResponseEntity<DeliverGuyResponsePrivate>seeCredentials(Authentication authentication){
        DeliverGuyResponsePrivate deliverGuyResponsePrivate = deliveryGuyService.seeCredentials(authentication);

        return ResponseEntity.ok().body(deliverGuyResponsePrivate);
    }
    @PostMapping("/{restaurantName}/create")
    public ResponseEntity<DeliveryGuyResponse>create(@RequestBody DeliveryGuyCreateRequest deliveryGuyDto,@PathVariable String restaurantName){
        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyService.create(deliveryGuyDto,restaurantName);

        return ResponseEntity.ok().body(deliveryGuyResponse);
    }
    @GetMapping("/seeCarts")
    public ResponseEntity<List<CartDeliveryGuy>>viewCarts(Authentication authentication){
        List<CartDeliveryGuy>cartsResponses = deliveryGuyService.viewCarts(authentication);

        return ResponseEntity.ok().body(cartsResponses);
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<DeliveryGuyResponse>update(@RequestParam boolean available, Authentication authentication){

        DeliveryGuyResponse deliveryGuyResponse =deliveryGuyService.update(available,authentication);

        return ResponseEntity.ok().body(deliveryGuyResponse);
    }
    @PostMapping("/{cartId}/delivered")
    public ResponseEntity<String>deliveredCart(Authentication authentication,@RequestParam boolean delivered,@PathVariable String cartId){
        String deliveredCart = deliveryGuyService.deliveredCart(authentication,delivered,cartId);

        return ResponseEntity.ok(deliveredCart);
    }

}

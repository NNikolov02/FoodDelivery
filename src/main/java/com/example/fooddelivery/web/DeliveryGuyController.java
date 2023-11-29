package com.example.fooddelivery.web;

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
    @GetMapping("")
    public ResponseEntity<DeliverGuyResponsePrivate>seeCredentials(Authentication authentication){
        DeliverGuyResponsePrivate deliverGuyResponsePrivate = deliveryGuyService.seeCredentials(authentication);

        return ResponseEntity.ok().body(deliverGuyResponsePrivate);
    }
    @PostMapping("/create")
    public ResponseEntity<DeliveryGuyResponse>create(@RequestBody DeliveryGuyCreateRequest deliveryGuyDto){
        DeliveryGuyResponse deliveryGuyResponse = deliveryGuyService.create(deliveryGuyDto);

        return ResponseEntity.ok().body(deliveryGuyResponse);
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<DeliveryGuyResponse>update(@RequestParam boolean available, Authentication authentication){

        DeliveryGuyResponse deliveryGuyResponse =deliveryGuyService.update(available,authentication);

        return ResponseEntity.ok().body(deliveryGuyResponse);
    }

}

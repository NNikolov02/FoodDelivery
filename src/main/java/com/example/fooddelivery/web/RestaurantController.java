package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantApiPage;
import com.example.fooddelivery.dto.restaurant.RestaurantCreateRequest;
import com.example.fooddelivery.dto.restaurant.RestaurantResponse;
import com.example.fooddelivery.mapping.RestaurantMapper;
import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.service.MenuService;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/delivery/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantMapper restaurantMapper;
    @Autowired
    private MenuService menuService;
    @GetMapping(value = "", produces = "application/json")
    public RestaurantApiPage<RestaurantResponse> getAllRestaurants(
            @RequestParam(required = false, defaultValue = "1") Integer currPage) {

        Page<RestaurantResponse> restaurantPage = restaurantService.fetchAll(currPage - 1, 10).map(restaurantMapper::responseFromModelOne);

        return new RestaurantApiPage<>(restaurantPage);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<RestaurantResponse>create(@RequestBody RestaurantCreateRequest restaurantDto){
        RestaurantResponse restaurantResponse = restaurantService.create(restaurantDto);

        return ResponseEntity.ok().body(restaurantResponse);
    }
    @GetMapping(value = "/{restaurantName}/deliverGuys")
    public ResponseEntity<List<DeliveryGuyResponse>>seeDeliveryGuys(@PathVariable String restaurantName){
        List<DeliveryGuyResponse>deliveryGuyResponses = restaurantService.seeDeliveryGuys(restaurantName);

        return ResponseEntity.ok().body(deliveryGuyResponses);
    }

    @GetMapping(value = "/name/{restaurantName}")
    public ResponseEntity<RestaurantResponse>findByName(@PathVariable String restaurantName){
        Restaurant restaurant = restaurantService.findRestaurantByName(restaurantName);

        return ResponseEntity.ok(restaurantMapper.responseFromModelOne(restaurant));
    }
    @GetMapping(value = "/location/{restaurantLocation}")
    public ResponseEntity<RestaurantResponse>findByLocation(@PathVariable String restaurantLocation){
        Restaurant restaurant = restaurantService.findRestaurantByLocation(restaurantLocation);

        return ResponseEntity.ok(restaurantMapper.responseFromModelOne(restaurant));
    }
    @DeleteMapping(value = "{restaurantName}")
    public ResponseEntity<String> deleteByName(@PathVariable String restaurantName){
        restaurantService.deleteByName(restaurantName);

        return ResponseEntity.ok("It is deleted!");
    }

    @PostMapping(value = "/{restaurantName}")
    public ResponseEntity<String>putMenu(@RequestParam String id,@PathVariable String restaurantName){
        Menu menu = menuService.findById(id);
        Restaurant restaurant =restaurantService.findRestaurantByName(restaurantName);
       String response = restaurantService.putMenu(menu,restaurant);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping(value = "/{restaurantName}")
    public ResponseEntity<String>putDeliveryGuy(@RequestParam String firstName,@RequestParam String lastName,@PathVariable String restaurantName){
        String restaurantResponse = restaurantService.putDeliveryGuy(restaurantName,firstName,lastName);

        return ResponseEntity.ok().body(restaurantResponse);

    }
    @PutMapping(value = "/rating/{restaurantName}")
    public ResponseEntity<RestaurantResponse>putRating(@RequestParam Integer rating,@PathVariable String restaurantName, Authentication authentication){
        RestaurantResponse restaurantResponse = restaurantService.putRating(restaurantName,rating,authentication);

        return ResponseEntity.ok().body(restaurantResponse);

    }



}

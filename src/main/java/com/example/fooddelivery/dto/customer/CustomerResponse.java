package com.example.fooddelivery.dto.customer;

import com.example.fooddelivery.dto.cart.CartDto;
import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private Boolean hasRated;
    private String phoneNumber;

    private String address;
    private  String url;
    private CartDto cart;
//    private List<RestaurantDto>restaurants;
}

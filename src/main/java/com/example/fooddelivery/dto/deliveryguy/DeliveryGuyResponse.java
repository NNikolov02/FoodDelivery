package com.example.fooddelivery.dto.deliveryguy;

import com.example.fooddelivery.dto.cart.CartDto;
import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import com.example.fooddelivery.model.Cart;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeliveryGuyResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private boolean available;

    private RestaurantDto restaurant;
    private CartDto cart;
}

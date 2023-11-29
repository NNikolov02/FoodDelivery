package com.example.fooddelivery.dto.deliveryguy;

import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeliverGuyResponsePrivate {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean available;

    private RestaurantDto restaurant;
}

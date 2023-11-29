package com.example.fooddelivery.dto.restaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantCreateRequest {
    private String name;

    private String description;

    private String location;
}

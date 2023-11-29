package com.example.fooddelivery.dto.restaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantDto {
    private String name;
    private String location;
}

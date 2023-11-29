package com.example.fooddelivery.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantRatingDto {
    private Integer rating;
}

package com.example.fooddelivery.dto.restaurant;

import com.example.fooddelivery.dto.RestaurantRatingDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantResponse {
    private String name;

    private String description;

    private String location;
    private Integer rating;
    private String menuUrl;
    private List<RestaurantRatingDto>ratings;
}

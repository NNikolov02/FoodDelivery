package com.example.fooddelivery.dto.steak;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SteakResponse {
    private UUID id;
    private String name;
    private String steakType;
    private String cookingLevel;
    private Integer price;
    private String url;
    private String addToCart;
    private Integer rating;
}

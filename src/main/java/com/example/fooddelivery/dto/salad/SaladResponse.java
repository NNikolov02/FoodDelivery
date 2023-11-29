package com.example.fooddelivery.dto.salad;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SaladResponse {
    private UUID id;
    private String name;
    @ElementCollection
    private List<String> ingredients;
    private boolean vegetarian;
    private Integer price;
    private String url;
    private String addToCart;
    private Integer rating;
}

package com.example.fooddelivery.dto.pizza;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PizzaResponse {
    private UUID id;
    private String name;

    private String pizzaSize;
    @ElementCollection
    private List<String> toppings;
    private Integer price;

    private Integer rating;
    private String url;
    private String addToCart;
}

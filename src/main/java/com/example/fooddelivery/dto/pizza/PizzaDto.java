package com.example.fooddelivery.dto.pizza;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PizzaDto {
    private String name;

    private String pizzaSize;
    private Integer price;
    private String url;
}

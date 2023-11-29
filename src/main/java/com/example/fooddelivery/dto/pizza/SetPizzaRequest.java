package com.example.fooddelivery.dto.pizza;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SetPizzaRequest {
    private String name;

//    private String pizzaSize;
//    @ElementCollection
//    private List<String> toppings;
}

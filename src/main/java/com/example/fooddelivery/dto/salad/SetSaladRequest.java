package com.example.fooddelivery.dto.salad;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SetSaladRequest {
    private String name;
//    @ElementCollection
//    private List<String> ingredients;
//    private boolean isVegetarian;
}

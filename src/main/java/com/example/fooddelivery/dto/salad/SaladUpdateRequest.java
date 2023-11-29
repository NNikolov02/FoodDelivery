package com.example.fooddelivery.dto.salad;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaladUpdateRequest {
    private String name;
    @ElementCollection
    private List<String> ingredients;
    private boolean vegetarian;
    private Integer price;
}

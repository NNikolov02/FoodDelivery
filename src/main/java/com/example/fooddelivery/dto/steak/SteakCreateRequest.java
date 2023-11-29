package com.example.fooddelivery.dto.steak;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SteakCreateRequest {
    private String name;
    private String steakType;
    private String cookingLevel;
    private Integer price;
}

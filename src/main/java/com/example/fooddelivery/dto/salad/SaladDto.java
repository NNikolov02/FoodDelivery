package com.example.fooddelivery.dto.salad;

import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaladDto {

    private String name;
    private boolean vegetarian;
    private Integer price;
    private String url;
    private Integer amount;
}

package com.example.fooddelivery.dto.pasta;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PastaResponse {
    private UUID id;
    private String name;
    private String pastaType;
    private String sauce;
    private Integer price;
    private String addToCart;
    private String url;
    private Integer rating;
}

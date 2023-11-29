package com.example.fooddelivery.dto.pasta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PastaCreateRequest {
    private String name;
    private String pastaType;
    private String sauce;
    private Integer price;
}

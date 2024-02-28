package com.example.fooddelivery.dto.pasta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PastaDto {
    private String name;
    private Integer price;
    private String url;
    private Integer amount;
}

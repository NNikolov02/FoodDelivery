package com.example.fooddelivery.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CartDto {
    private UUID id;
}

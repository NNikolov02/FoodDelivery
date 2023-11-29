package com.example.fooddelivery.error;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FoodDeliveryException extends RuntimeException {

    private final UUID errorId;

    public FoodDeliveryException(String message) {
        super(message);
        this.errorId = UUID.randomUUID();
    }
}
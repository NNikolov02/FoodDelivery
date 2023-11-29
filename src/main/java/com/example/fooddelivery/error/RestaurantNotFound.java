package com.example.fooddelivery.error;

public class RestaurantNotFound extends RuntimeException{

    public RestaurantNotFound(String message){
        super(message);
    }
}

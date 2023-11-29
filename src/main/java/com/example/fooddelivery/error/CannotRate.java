package com.example.fooddelivery.error;

public class CannotRate extends RuntimeException{

    public CannotRate(String message){
        super(message);
    }
}

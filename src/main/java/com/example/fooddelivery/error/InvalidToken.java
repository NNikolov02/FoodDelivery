package com.example.fooddelivery.error;

public class InvalidToken extends RuntimeException{
    public InvalidToken(String message){
        super(message);
    }
}

package com.example.fooddelivery.error;

public class NotDeletedException extends RuntimeException{

    public NotDeletedException(String message){
        super(message);
    }
}

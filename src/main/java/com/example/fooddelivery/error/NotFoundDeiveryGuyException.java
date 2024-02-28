package com.example.fooddelivery.error;

public class NotFoundDeiveryGuyException extends RuntimeException{
    public NotFoundDeiveryGuyException(String message){
        super(message);
    }
}

package com.example.fooddelivery.error;

public class NotFoundCustomerException extends RuntimeException{
    public NotFoundCustomerException(String message){
        super(message);
    }
}

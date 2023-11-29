package com.example.fooddelivery.error;

import lombok.Getter;

@Getter
public class NotFoundObjectException extends FoodDeliveryException{

    private final String objectClass;
    private final String id;

    public NotFoundObjectException(String message, String objectClass, String id){
        super(message);
        this.objectClass = objectClass;
        this.id = id;
    }



}

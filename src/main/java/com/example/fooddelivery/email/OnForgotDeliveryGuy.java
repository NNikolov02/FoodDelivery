package com.example.fooddelivery.email;

import com.example.fooddelivery.model.DeliveryGuy;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnForgotDeliveryGuy extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private DeliveryGuy deliveryGuy;


    public OnForgotDeliveryGuy(DeliveryGuy deliveryGuy, Locale locale, String appUrl) {
        super(deliveryGuy);
        this.deliveryGuy =deliveryGuy;
        this.locale = locale;
        this.appUrl = appUrl;

    }




    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public DeliveryGuy getDeliveryGuy() {
        return deliveryGuy;
    }

    public void setDeliveryGuy(DeliveryGuy deliveryGuy) {
        this.deliveryGuy = deliveryGuy;
    }
}

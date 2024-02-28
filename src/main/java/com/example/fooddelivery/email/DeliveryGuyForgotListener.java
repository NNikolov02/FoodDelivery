package com.example.fooddelivery.email;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.service.DeliveryGuyService;
import com.example.fooddelivery.service.EmailService;
import com.fasterxml.jackson.core.SerializableString;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DeliveryGuyForgotListener implements ApplicationListener<OnForgotDeliveryGuy> {

    private final DeliveryGuyService deliveryGuyService;
    private final  EmailService emailService;

    public DeliveryGuyForgotListener(DeliveryGuyService deliveryGuyService, EmailService emailService) {
        this.deliveryGuyService = deliveryGuyService;
        this.emailService = emailService;
    }
    @Override
    public void onApplicationEvent(OnForgotDeliveryGuy event) {
        this.forgotPassword(event);

    }
    private void forgotPassword(OnForgotDeliveryGuy event) {
        DeliveryGuy deliveryGuy = event.getDeliveryGuy();
        List<String>tokens = deliveryGuy.getTokens();
        String token = UUID.randomUUID().toString();
        deliveryGuyService.createVerificationToken(deliveryGuy, token);
        tokens.add(token);


        String recipientAddress = deliveryGuy.getEmail();
        String subject = "Change Password";
        String confirmationUrl = "http://localhost:8084/delivery/deliveryGuy" + "/confirm?token=" + token;
        String message = "Please click on the link below to change your password:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
        deliveryGuyService.save(deliveryGuy);

    }
}

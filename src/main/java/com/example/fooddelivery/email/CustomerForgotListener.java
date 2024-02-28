package com.example.fooddelivery.email;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.service.CustomerService;
import com.example.fooddelivery.service.DeliveryGuyService;
import com.example.fooddelivery.service.EmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerForgotListener implements ApplicationListener<OnForgotCustomer> {

    private final CustomerService customerService;
    private final EmailService emailService;

    public CustomerForgotListener(CustomerService customerService, EmailService emailService) {
        this.customerService = customerService;
        this.emailService = emailService;
    }
    @Override
    public void onApplicationEvent(OnForgotCustomer event) {
        this.forgotPassword(event);

    }
    private void forgotPassword(OnForgotCustomer event) {
        Customer customer = event.getCustomer();
        List<String> tokens = customer.getTokens();
        String token = UUID.randomUUID().toString();
        customerService.createVerificationToken(customer, token);
        tokens.add(token);


        String recipientAddress = customer.getEmail();
        String subject = "Change Password";
        String confirmationUrl = "http://localhost:8084/delivery/customer" + "/confirm?token=" + token;
        String message = "Please click on the link below to change your password:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
        customerService.save(customer);

    }
}

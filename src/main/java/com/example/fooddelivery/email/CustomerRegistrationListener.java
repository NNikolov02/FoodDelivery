package com.example.fooddelivery.email;



import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.service.CustomerService;
import com.example.fooddelivery.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerRegistrationListener implements ApplicationListener<OnRegistrationCompleteEventCustomer> {

    private final CustomerService service;
    private final EmailService emailService;


    @Autowired
    public CustomerRegistrationListener(CustomerService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;

    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEventCustomer event) {
        this.confirmRegistration(event);

    }

    private void confirmRegistration(OnRegistrationCompleteEventCustomer event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(customer, token);

        String recipientAddress = customer.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/confirmRegistration?token=" + token;
        String message = "Thank you for registering. Please click on the link below to verify your email address:\n"
                + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }

}


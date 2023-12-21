package com.example.fooddelivery.email;

import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.CustomerRepository;
import com.example.fooddelivery.service.CartService;
import com.example.fooddelivery.service.CustomerService;
import com.example.fooddelivery.service.DeliveryGuyService;
import com.example.fooddelivery.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Component
public class DeliveryGuyCartListener implements ApplicationListener<OnCartDeliveryGuy> {


    private final EmailService emailService;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CartService cartService;


    @Autowired
    public DeliveryGuyCartListener(EmailService emailService) {
        this.emailService = emailService;

    }

    @Override
    public void onApplicationEvent(OnCartDeliveryGuy event) {
        this.confirmRegistration(event);

    }

    private void confirmRegistration(OnCartDeliveryGuy event) {
        DeliveryGuy deliveryGuy = event.getDeliveryGuy();
        String token = UUID.randomUUID().toString();
        Customer customer = customerRepo.findCustomerByCartDeliveryGuy(deliveryGuy.getId());
        Cart cart = customer.getCart();
        List<Pizza> pizzas = cart.getPizzas();
        List<Pasta>pastas = cart.getPastas();
        List<Salad>salads = cart.getSalads();
        List<Steak>steaks = cart.getSteaks();


        String recipientAddress = deliveryGuy.getEmail();
        String subject = "New Cart To Deliver";
        String message = "Hello" + deliveryGuy.getFirstName() + "\n" + "A client:" + customer.getFirstName() + "" + customer.getLastName()
                + "has purchased in " +cart.getCreateTime() +"for address "+cart.getLocation() + "\n\n" + "Pizzas:" +"\n"+ cartService.pizzasName(pizzas) + "\n\n" + "Pastas" + "\n" +cartService.pastasName(pastas) + "\n\n"
                + "Salads" +"\n"+ cartService.saladName(salads) + "\n\n" + "Steaks" + "\n" + cartService.steaksName(steaks) + "\n\n"
                + "for" + cart.getFullPrice() + "$"
                + "\n\n" + "It must be delivered until:" + cart.getTimeOfDelivery()
                + "\n\n" + "For more information go to your cart-chart:" + "\n" + "http://localhost:8084/delivery/deliveryGuy/seeCarts" + "\n\n"
                + "Have a nice day!" + "\n" + deliveryGuy.getRestaurant().getName();
//                ;
//
        emailService.sendSimpleMessage(recipientAddress, subject, message);
    }

}

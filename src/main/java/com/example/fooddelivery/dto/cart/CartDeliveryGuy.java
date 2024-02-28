package com.example.fooddelivery.dto.cart;

import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyDto;
import com.example.fooddelivery.dto.pasta.PastaDto;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.salad.SaladDto;
import com.example.fooddelivery.dto.steak.SteakDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartDeliveryGuy {
    private UUID id;
    private String location;
    private LocalDateTime  createTime;
    private LocalDateTime timeOfDelivery;

    private String customerName;
    private String customerNumber;
    private DeliveryGuyDto deliveryGuy;
    private List<PizzaDto> pizzas;
    private List<SteakDto>steaks;
    private List<SaladDto>salads;
    private List<PastaDto>pastas;

    private Integer fullPrice;
    private String isDelivered;
}

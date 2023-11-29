package com.example.fooddelivery.dto.cart;

import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyDto;
import com.example.fooddelivery.dto.pasta.PastaDto;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.salad.SaladDto;
import com.example.fooddelivery.dto.steak.SteakDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartResponse {
    private UUID id;
    private String location;
    private LocalTime createTime;
    private LocalTime timeOfDelivery;

    private CustomerDto customer;
    private DeliveryGuyDto deliveryGuy;
    private List<PizzaDto>pizzas;
    private List<SteakDto>steaks;
    private List<SaladDto>salads;
    private List<PastaDto>pastas;

    private Integer fullPrice;
}

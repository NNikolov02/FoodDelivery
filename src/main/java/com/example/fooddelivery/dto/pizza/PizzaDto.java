package com.example.fooddelivery.dto.pizza;

import com.example.fooddelivery.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PizzaDto {
    private String name;

    private String pizzaSize;
    private Integer price;
    private String url;
//    @Column(name = "amount")
//    private Map<Customer, Integer> customerAmountMap = new HashMap<>();
    private Integer amount;
}

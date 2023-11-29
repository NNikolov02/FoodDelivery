package com.example.fooddelivery.dto.menu;

import com.example.fooddelivery.dto.pasta.PastaDto;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.salad.SaladDto;
import com.example.fooddelivery.dto.steak.SteakDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MenuResponse {
    private UUID id;
    private String name;
    private String description;
    private List<PizzaDto> pizzas;
    private List<SteakDto>steaks;
    private List<SaladDto>salads;
    private List<PastaDto>pastas;
}

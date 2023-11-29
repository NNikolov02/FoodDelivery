package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.pizza.PizzaUpdateRequest;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.model.Restaurant;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PizzaMapper {

    Pizza modelFromCreateRequest(PizzaCreateRequest pizzaDto);

    PizzaResponse responseFromModel(Pizza pizza);
    //    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    @Mapping(target = "pizzaSize", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "toppings", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromModel(PizzaUpdateRequest pizzaDto, @MappingTarget Pizza pizza);

    PizzaDto modelRoDto(Pizza pizza);

    Pizza dtoModel(Pizza pizzaDto);
}

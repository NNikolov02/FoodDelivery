package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.pizza.PizzaUpdateRequest;
import com.example.fooddelivery.dto.salad.SaladCreateRequest;
import com.example.fooddelivery.dto.salad.SaladDto;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.salad.SaladUpdateRequest;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.model.Salad;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SaladMapper {
    Salad modelFromCreateRequest(SaladCreateRequest saladDto);

    SaladResponse responseFromModel(Salad salad);
    //    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    @Mapping(target = "vegetarian", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ingredients", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromModel(SaladUpdateRequest saladDto, @MappingTarget Salad salad);

    SaladDto modelRoDto(Salad salad);

    Salad dtoModel(Salad saladDto);
}

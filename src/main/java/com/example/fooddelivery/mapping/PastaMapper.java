package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.pasta.PastaCreateRequest;
import com.example.fooddelivery.dto.pasta.PastaDto;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pasta.PastaUpdateRequest;
import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.pizza.PizzaUpdateRequest;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Pizza;
import jakarta.validation.constraints.Past;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PastaMapper {
    Pasta modelFromCreateRequest(PastaCreateRequest pastaDto);

    PastaResponse responseFromModel(Pasta pasta);
    //    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    @Mapping(target = "pastaType", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "sauce", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromModel(PastaUpdateRequest pastaDto, @MappingTarget Pasta pasta);

    PastaDto modelRoDto(Pasta pasta);

    Pasta dtoModel(Pasta pastaDto);
}

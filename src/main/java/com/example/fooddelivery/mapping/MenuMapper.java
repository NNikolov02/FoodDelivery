package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.customer.CustomerResponse;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.dto.menu.MenuCreateRequest;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.dto.menu.MenuResponse;
import com.example.fooddelivery.dto.menu.MenuUpdateRequest;
import com.example.fooddelivery.dto.pasta.PastaDto;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pizza.PizzaDto;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.salad.SaladDto;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.steak.SteakDto;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.model.*;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapper {
    Menu modelFromCreateRequest(MenuCreateRequest menuDto);

    MenuResponse responseFromModelOne(Menu menu);
//    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    List<PizzaResponse>responseFromModelPizza(List<Pizza> pizzas);
    List<PastaResponse>responseFromModelPasta(List<Pasta> pastas);
    List<SaladResponse>responseFromModelSalad(List<Salad> salads);
    List<SteakResponse>responseFromModelSteak(List<Steak> steaks);



    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(MenuUpdateRequest menuDto, @MappingTarget Menu menu);
    MenuDto modelRoDto(Menu menu);

    Menu dtoModel(Menu menuDto);
}

package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.customer.CustomerResponse;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.dto.deliveryguy.DeliveryGuyResponse;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantCreateRequest;
import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import com.example.fooddelivery.dto.restaurant.RestaurantResponse;
import com.example.fooddelivery.dto.restaurant.RestaurantUpdateRequest;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.DeliveryGuyRepository;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {
    Restaurant modelFromCreateRequest(RestaurantCreateRequest restaurantDto);

    RestaurantResponse responseFromModelOne(Restaurant restaurant);
//    List<CustomerResponse> responseFromModelList(List<Customer> customers);

    List<DeliveryGuyResponse>responseFromModelDeliveryGuy(List<DeliveryGuy> deliveryGuys);

    @Mapping(target = "location", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(RestaurantUpdateRequest restaurantUpdateDto, @MappingTarget Restaurant restaurant);
    RestaurantDto modelRoDto(Restaurant restaurant);

    Restaurant dtoModel(Restaurant restaurantDto);
}

package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.cart.CartCreateRequest;
import com.example.fooddelivery.dto.cart.CartDto;
import com.example.fooddelivery.dto.cart.CartResponse;
import com.example.fooddelivery.dto.customer.CustomerCreateRequest;
import com.example.fooddelivery.dto.customer.CustomerDto;
import com.example.fooddelivery.dto.customer.CustomerResponse;
import com.example.fooddelivery.dto.customer.CustomerUpdateRequest;
import com.example.fooddelivery.dto.restaurant.RestaurantDto;
import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.Restaurant;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    Cart modelFromCreateRequest(CartCreateRequest cartDto);

    CartResponse responseFromModelOne(Cart cart);
//    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    CartDto modelRoDto(Cart cart);

    Cart dtoModel(Cart cartDto);



}

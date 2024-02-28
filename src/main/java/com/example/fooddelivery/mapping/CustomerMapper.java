package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.customer.*;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    Customer modelFromCreateRequest(CustomerCreateRequest customerCreateDto);

    CustomerResponse responseFromModelOne(Customer customer);
    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    CustomerResponsePrivate responseFromModelPrivate(Customer customer);


    @Mapping(target = "email",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "phoneNumber",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "firstName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lastName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(CustomerUpdateRequest customerUpdateDto, @MappingTarget Customer customer);
    CustomerDto modelRoDto(Customer customer);

    Customer dtoModel(Customer customerDto);
    @Mapping(target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDtoPassword(String password, @MappingTarget Customer customer);

}
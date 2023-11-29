package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.deliveryguy.*;
import com.example.fooddelivery.model.DeliveryGuy;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryGuyMapper {

    DeliveryGuy modelFromCreateRequest(DeliveryGuyCreateRequest deliveryGuyDto);

    DeliveryGuyResponse responseFromModelOne(DeliveryGuy deliveryGuy);
    DeliverGuyResponsePrivate responseFromModel(DeliveryGuy deliveryGuy);
//    List<CustomerResponse> responseFromModelList(List<Customer> customers);



    @Mapping(target = "available", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromDto(Boolean available, @MappingTarget DeliveryGuy deliveryGuy);
    DeliveryGuyDto modelRoDto(DeliveryGuy deliveryGuy);

    DeliveryGuy dtoModel(DeliveryGuy deliveryGuyDto);
}

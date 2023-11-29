package com.example.fooddelivery.mapping;

import com.example.fooddelivery.dto.steak.SteakCreateRequest;
import com.example.fooddelivery.dto.steak.SteakDto;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.dto.steak.SteakUpdateRequest;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.model.Steak;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SteakMapper {

    Steak modelFromCreateRequest(SteakCreateRequest steakDto);

    SteakResponse responseFromModel(Steak steak);
    //    List<CustomerResponse> responseFromModelList(List<Customer> customers);
    @Mapping(target = "steakType", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cookingLevel", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromUpdateRequest(SteakUpdateRequest steakDto,@MappingTarget Steak steak);

    SteakDto modelFromDto(Steak steak);
    Steak dtoModel(Steak steakDto);

}

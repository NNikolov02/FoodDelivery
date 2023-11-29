package com.example.fooddelivery.dto.deliveryguy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryGuyDto {
    private String firstName;
    private String lastName;
}

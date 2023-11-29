package com.example.fooddelivery.dto.deliveryguy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DeliveryGuyUpdateRequest {
    private boolean available;
    private String password;
}

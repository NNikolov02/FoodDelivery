package com.example.fooddelivery.dto.deliveryguy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryGuyCreateRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean available;
}

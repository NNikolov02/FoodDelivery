package com.example.fooddelivery.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCreateRequest {
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String address;
}

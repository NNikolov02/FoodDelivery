package com.example.fooddelivery.dto.customer;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    private String username;

    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String address;
    private  String url;
}

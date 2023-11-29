package com.example.fooddelivery.dto.menu;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuUpdateRequest {
    private String name;
    private String description;
}

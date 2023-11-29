package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.salad.SaladCreateRequest;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.dto.steak.SteakCreateRequest;
import com.example.fooddelivery.dto.steak.SteakResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.SaladMapper;
import com.example.fooddelivery.mapping.SteakMapper;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.model.Steak;
import com.example.fooddelivery.service.SaladService;
import com.example.fooddelivery.service.SteakService;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delivery/steak")
public class SteakController {

    @Autowired
    private SteakMapper steakMapper;
    @Autowired
    private ObjectValidator validator;

    @Autowired
    private SteakService steakService;


    @PostMapping(value = "/create")
    public ResponseEntity<SteakResponse> createSteak(@RequestBody SteakCreateRequest steakDto){
        Map<String, String> validationErrors = validator.validate(steakDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Steak Create", validationErrors);
        }
        Steak steak = steakMapper.modelFromCreateRequest(steakDto);
        steak.setUrl("http://localhost:8084/delivery/menu/steak/" + steak.getName());
        steak.setAddToCart("http://localhost:8084/delivery/cart/steak?steakName=" + steak.getName());
        steakService.save(steak);
        SteakResponse steakResponse = steakMapper.responseFromModel(steak);

        return ResponseEntity.ok().body(steakResponse);


    }
}

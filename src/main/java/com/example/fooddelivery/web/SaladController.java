package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.pasta.PastaCreateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.salad.SaladCreateRequest;
import com.example.fooddelivery.dto.salad.SaladResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.SaladMapper;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.service.SaladService;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delivery/salad")
public class SaladController {

    @Autowired
    private SaladMapper saladMapper;
    @Autowired
    private ObjectValidator validator;

    @Autowired
    private SaladService saladService;


    @PostMapping(value = "")
    public ResponseEntity<SaladResponse> createSalad(@RequestBody SaladCreateRequest saladDto){
        Map<String, String> validationErrors = validator.validate(saladDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Salad Create", validationErrors);
        }
        Salad salad = saladMapper.modelFromCreateRequest(saladDto);
        salad.setUrl("http://localhost:8084/delivery/menu/salad/" + salad.getName());
        salad.setAddToCart("http://localhost:8084/delivery/cart/salad?saladName=" + salad.getName());
        saladService.save(salad);
        SaladResponse saladResponse = saladMapper.responseFromModel(salad);

        return ResponseEntity.ok().body(saladResponse);


    }
}

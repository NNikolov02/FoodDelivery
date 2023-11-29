package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.PizzaMapper;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.repository.PizzaRepository;
import com.example.fooddelivery.service.PizzaService;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delivery/pizza")
public class PizzaController {
    @Autowired
    private ObjectValidator validator;
    @Autowired
    private PizzaMapper pizzaMapper;
    @Autowired
    private PizzaService pizzaService;

    @PostMapping(value = "/create")
    public ResponseEntity<PizzaResponse>createPizza(@RequestBody PizzaCreateRequest pizzaDto){
        Map<String, String> validationErrors = validator.validate(pizzaDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Pizza Create", validationErrors);
        }
        Pizza pizza = pizzaMapper.modelFromCreateRequest(pizzaDto);
        pizza.setUrl("http://localhost:8084/delivery/menu/pizza/" + pizza.getName());
        pizza.setAddToCart("http://localhost:8084/delivery/cart/pizza?pizzaName=" + pizza.getName());
        pizzaService.save(pizza);
        PizzaResponse pizzaResponse = pizzaMapper.responseFromModel(pizza);

        return ResponseEntity.ok().body(pizzaResponse);


    }
}

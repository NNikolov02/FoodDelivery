package com.example.fooddelivery.web;

import com.example.fooddelivery.dto.pasta.PastaCreateRequest;
import com.example.fooddelivery.dto.pasta.PastaResponse;
import com.example.fooddelivery.dto.pizza.PizzaCreateRequest;
import com.example.fooddelivery.dto.pizza.PizzaResponse;
import com.example.fooddelivery.error.InvalidObjectException;
import com.example.fooddelivery.mapping.PastaMapper;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.repository.PastaRepository;
import com.example.fooddelivery.service.PastaService;
import com.example.fooddelivery.validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delivery/pasta")
public class PastaController {

    @Autowired
    private PastaMapper pastaMapper;

    @Autowired
    private ObjectValidator validator;
    @Autowired
    private PastaService pastaService;


    @PostMapping(value = "/create")
    public ResponseEntity<PastaResponse> createPasta(@RequestBody PastaCreateRequest pastaDto){
        Map<String, String> validationErrors = validator.validate(pastaDto);
        if (validationErrors.size() != 0) {
            throw new InvalidObjectException("Invalid Pasta Create", validationErrors);
        }
        Pasta pasta = pastaMapper.modelFromCreateRequest(pastaDto);
        pasta.setUrl("http://localhost:8084/delivery/menu/pasta/" + pasta.getName());
        pasta.setAddToCart("http://localhost:8084/delivery/cart/pasta?pastaName=" + pasta.getName());
        pastaService.save(pasta);
        PastaResponse pastaResponse = pastaMapper.responseFromModel(pasta);

        return ResponseEntity.ok().body(pastaResponse);


    }
}

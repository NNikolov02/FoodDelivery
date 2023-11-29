package com.example.fooddelivery.service;

import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class PizzaService {
    @Autowired
    private PizzaRepository repo;

    public Pizza save(Pizza pizza){
        return repo.save(pizza);
    }
}

package com.example.fooddelivery.service;

import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.repository.PastaRepository;
import com.example.fooddelivery.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class PastaService {

    @Autowired
    private PastaRepository repo;

    public Pasta save(Pasta pasta){
        return repo.save(pasta);
    }
}

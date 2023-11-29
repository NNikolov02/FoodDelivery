package com.example.fooddelivery.service;

import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.repository.SaladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class SaladService {

    @Autowired
    private SaladRepository repo;

    public Salad save(Salad salad){
        return repo.save(salad);
    }
}

package com.example.fooddelivery.service;

import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.model.Steak;
import com.example.fooddelivery.repository.SaladRepository;
import com.example.fooddelivery.repository.SteakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class SteakService {

    @Autowired
    private SteakRepository repo;

    public Steak save(Steak steak){
        return repo.save(steak);
    }
}

package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {

    Restaurant findByName(String name);
    void deleteByName(String name);
    Restaurant findByLocation(String location);

}

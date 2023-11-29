package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

    Menu findByName(String name);
    void deleteByName(String name);
//    @Query("SELECT d FROM Menu d JOIN d.restaurant c WHERE c.name = :restaurantName")
//    Menu findByRestaurantName(@Param("restaurantName") String restaurantName);
}

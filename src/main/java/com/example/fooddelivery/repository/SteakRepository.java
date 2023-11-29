package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.model.Steak;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SteakRepository extends CrudRepository<Steak, UUID> {
    @Query("SELECT s FROM Steak s JOIN s.menu c WHERE c.id = :id")
    List<Steak> findByMenuId(@Param("id") UUID id);
    Steak findByName(String name);
    void deleteByName(String name);

    List<Steak>findByCookingLevel(String cookingLevel);
}

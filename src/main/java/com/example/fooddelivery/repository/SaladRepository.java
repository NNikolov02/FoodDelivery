package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.model.Salad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaladRepository extends CrudRepository<Salad, UUID> {
    @Query("SELECT s FROM Salad s JOIN s.menu c WHERE c.id = :id")
    List<Salad> findByMenuId(@Param("id") UUID id);
    Salad findByName(String name);
    void deleteByName(String name);

    List<Salad>findByVegetarian(boolean vegetarian);
}

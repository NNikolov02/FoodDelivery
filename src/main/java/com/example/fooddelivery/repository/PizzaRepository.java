package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Pizza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, UUID> {
    @Query("SELECT p FROM Pizza p JOIN p.menu c WHERE c.id = :id")
    List<Pizza> findByMenuId(@Param("id") UUID id);

    Pizza findByName(String name);
    void deleteByName(String name);
}

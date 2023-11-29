package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Pizza;
import jakarta.validation.constraints.Past;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PastaRepository extends CrudRepository<Pasta, UUID> {
    @Query("SELECT p FROM Pasta p JOIN p.menu c WHERE c.id = :id")
    List<Pasta> findByMenuId(@Param("id") UUID id);
    Pasta findByName(String name);
    void deleteByName(String name);
    List<Pasta> findByPastaType(String pastaType);
}

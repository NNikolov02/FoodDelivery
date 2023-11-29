package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {
    @Query("SELECT a FROM Cart a JOIN a.customer c WHERE c.username = :customerName")
    void deleteByCustomerUsername(@Param("customerName") String customerName);
    @Query("SELECT a FROM Cart a JOIN a.pizzas c WHERE c.name= :pizzaName")
    Cart findByPizzasName(@Param("pizzaName") String pizzaName);


    @Query("SELECT a FROM Cart a JOIN a.customer c WHERE c.username = :customerName")
    Cart findByCustomersName(@Param("customerName") String customerName);

    @Query("SELECT a FROM Cart a JOIN a.customer c WHERE c.username = :customerName")
    void deleteByCustomerName(@Param("customerName") String customerName);
}

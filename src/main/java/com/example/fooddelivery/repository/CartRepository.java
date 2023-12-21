package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Cart a WHERE a.customer IN (SELECT c FROM Customer c WHERE c.username = :customerName)")
    void deleteByCustomerUsername(@Param("customerName") String customerName);
    @Query("SELECT a FROM Cart a JOIN a.pizzas c WHERE c.name= :pizzaName")
    Cart findByPizzasName(@Param("pizzaName") String pizzaName);


    @Query("SELECT a FROM Cart a JOIN a.customer c WHERE c.username = :customerName")
    Cart findByCustomersUsername(@Param("customerName") String customerName);
    @Query("SELECT a FROM Cart a JOIN a.customer c WHERE c.id = :customerId")
    Cart findByCustomersId(@Param("customerId") UUID customerId);
}

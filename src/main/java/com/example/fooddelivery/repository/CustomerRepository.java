package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Customer findByEmail(String email);

    Customer findCustomerByUsername(String name);
    void deleteCustomerByUsername(String name);
}

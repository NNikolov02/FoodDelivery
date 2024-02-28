package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Customer findByEmail(String email);

    Customer findCustomerByUsername(String name);
    Customer findByTokensIn(List<String> tokens);
    void deleteCustomerByUsername(String name);
//    @Query("SELECT c FROM Customer c JOIN c.Cart c WHERE c.username = :customerName")
//    Customer findByCartDeliveryGuyUsername()

    @Query("SELECT c FROM Customer c JOIN c.cart a JOIN a.deliveryGuy d WHERE d.id = :deliveryGuyId")
    Customer findCustomerByCartDeliveryGuy(@Param("deliveryGuyId") UUID id);
}

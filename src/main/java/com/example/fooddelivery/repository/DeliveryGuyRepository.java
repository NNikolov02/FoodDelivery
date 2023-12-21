package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.DeliveryGuy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryGuyRepository extends CrudRepository<DeliveryGuy, UUID> {

    DeliveryGuy findByUsername(String name);
    DeliveryGuy findByFirstNameAndLastName(String fisrtName,String lastName);
    void deleteByUsername(String name);

    List<DeliveryGuy>findByAvailable(boolean available);

    @Query("SELECT d FROM DeliveryGuy d JOIN d.restaurant c WHERE c.name = :restaurantName")
    List<DeliveryGuy>findByRestaurantName(@Param("restaurantName") String restaurantName);
    @Query("SELECT d FROM DeliveryGuy d JOIN d.restaurant c WHERE c.name = :restaurantName")
    DeliveryGuy findByRestaurantNameOne(@Param("restaurantName") String restaurantName);
//    @Query("SELECT d FROM DeliveryGuy d JOIN d.cart c WHERE c.id = :cartId")
//    DeliveryGuy findByCartId(@Param("cartId") UUID cartId);


}

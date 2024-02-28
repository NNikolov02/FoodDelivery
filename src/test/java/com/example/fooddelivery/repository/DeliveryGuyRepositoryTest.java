package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class DeliveryGuyRepositoryTest {

    @Autowired
    private DeliveryGuyRepository deliveryGuyRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByUserName(){

        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(deliveryGuy);

        DeliveryGuy findDeliveryGuy = deliveryGuyRepo.findByUsername(deliveryGuy.getUsername());
        assertEquals(deliveryGuy,findDeliveryGuy);

    }
    @Test
    public void testFindByEmail(){

        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .email("nikinikolov2002@gmail.com")
                .build();
        entityManager.persistAndFlush(deliveryGuy);

        DeliveryGuy findDeliveryGuy = deliveryGuyRepo.findByEmail(deliveryGuy.getEmail());
        assertEquals(deliveryGuy,findDeliveryGuy);

    }
    @Test
    public void testFindByAvailable(){


        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .available(true)
                .build();
        entityManager.persistAndFlush(deliveryGuy);
        DeliveryGuy deliveryGuy2 = DeliveryGuy.builder()
                .available(true)
                .build();
        entityManager.persistAndFlush(deliveryGuy2);



        List<DeliveryGuy> findDeliveryGuys = deliveryGuyRepo.findByAvailable(true);


                assertThat(findDeliveryGuys).contains(deliveryGuy);
                assertThat(findDeliveryGuys).contains(deliveryGuy2);


    }
    @Test
    public void testFindByFirstNameAndLastName(){


        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .firstName("Nikola")
                .lastName("Ivanov")
                .build();
        entityManager.persistAndFlush(deliveryGuy);




        DeliveryGuy findDeliveryGuy = deliveryGuyRepo.findByFirstNameAndLastName(deliveryGuy.getFirstName(),deliveryGuy.getLastName());


        assertEquals(deliveryGuy,findDeliveryGuy);



    }
    @Test
    public void testFindByTokensIn(){
        List<String>tokens = new ArrayList<>();
        String token = UUID.randomUUID().toString();
        tokens.add(token);


        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .tokens(tokens)
                .build();
        entityManager.persistAndFlush(deliveryGuy);




        DeliveryGuy findDeliveryGuy = deliveryGuyRepo.findByTokensIn(tokens);


        assertEquals(deliveryGuy,findDeliveryGuy);



    }
    @Test
    public void testDeleteByUsername(){



        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(deliveryGuy);
        deliveryGuyRepo.deleteByUsername(deliveryGuy.getUsername());




        DeliveryGuy findDeliveryGuy = deliveryGuyRepo.findByUsername(deliveryGuy.getUsername());


        assertNull(findDeliveryGuy);



    }
    @Test
    public void testRestaurantName(){

        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(restaurant);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("Ivan")
                .build();
        deliveryGuy.setRestaurant(restaurant);
        entityManager.persistAndFlush(deliveryGuy);



        List<DeliveryGuy> findDeliveryGuys = deliveryGuyRepo.findByRestaurantName(restaurant.getName());


        assertThat(findDeliveryGuys).contains(deliveryGuy);



    }
//findByFirstNameAndLastName


}

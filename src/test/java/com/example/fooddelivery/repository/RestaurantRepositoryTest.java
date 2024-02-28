package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByName(){

        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(restaurant);

        Restaurant findRestaurant =restaurantRepo.findByName(restaurant.getName());
        assertEquals(restaurant,findRestaurant);

    }
    @Test
    public void testDeleteByName() {

        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(restaurant);
        restaurantRepo.deleteByName(restaurant.getName());

        Restaurant findRestaurant = restaurantRepo.findByName(restaurant.getName());
        assertNull(findRestaurant);

    }
    @Test
    public void testFindByLocation(){

        Restaurant restaurant = Restaurant.builder()
                .location("Sofia")
                .build();
        entityManager.persistAndFlush(restaurant);

        Restaurant findRestaurant =restaurantRepo.findByLocation(restaurant.getLocation());
        assertEquals(restaurant,findRestaurant);

    }

}

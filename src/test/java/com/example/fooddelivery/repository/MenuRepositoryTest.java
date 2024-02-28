package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.DeliveryGuy;
import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepo;
   @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByName(){

        Menu menu = Menu.builder()
                .name("Happy Menu")
                .build();
        entityManager.persistAndFlush(menu);

        Menu findMenu =menuRepo.findByName(menu.getName());
        assertEquals(menu,findMenu);

    }
    @Test
    public void testDeleteByName(){

        Menu menu = Menu.builder()
                .name("Happy Menu")
                .build();
        entityManager.persistAndFlush(menu);
        menuRepo.deleteByName(menu.getName());

        Menu findMenu =menuRepo.findByName(menu.getName());
       assertNull(findMenu);

    }
    @Test
    public void testFindByRestaurantName(){
        List<Restaurant>restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .name("Happy")
                .build();
        restaurants.add(restaurant);
        entityManager.persistAndFlush(restaurant);
        Menu menu = Menu.builder()
                .name("Happy Menu")
                .build();
        for(Restaurant restaurant1:restaurants) {
            menu.setRestaurants(restaurants);
            restaurant1.setMenu(menu);
            entityManager.persistAndFlush(menu);
            entityManager.persistAndFlush(restaurant);
        }

        Menu findMenu =menuRepo.findByRestaurantName(restaurant.getName());
        List<Restaurant>restaurants1 = findMenu.getRestaurants();
        for(Restaurant restaurant1:restaurants1) {
            assertEquals("Happy",restaurant1.getName());
        }

    }
}

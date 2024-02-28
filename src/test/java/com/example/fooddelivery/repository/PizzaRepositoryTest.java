package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Pizza;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class PizzaRepositoryTest {

    @Autowired
    private PizzaRepository pizzaRepo;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testFindByName(){

        Pizza pizza =Pizza.builder()
                .name("Peperoni")
                .build();
        entityManager.persistAndFlush(pizza);

        Pizza findPizza = pizzaRepo.findByName(pizza.getName());
        assertEquals(pizza,findPizza);

    }
    @Test
    public void testDeleteByName(){

        Pizza pizza =Pizza.builder()
                .name("Peperoni")
                .build();
        entityManager.persistAndFlush(pizza);
        pizzaRepo.deleteByName(pizza.getName());

        Pizza findPizza = pizzaRepo.findByName(pizza.getName());
        assertNull(findPizza);

    }
    @Test
    public void testFindByMenuId(){
        Menu menu = Menu.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(menu);

        Pizza pizza =Pizza.builder()
                .name("Peperoni")
                .build();
        pizza.setMenu(menu);
        entityManager.persistAndFlush(pizza);
        entityManager.persistAndFlush(menu);

        List<Pizza> findPizzas = pizzaRepo.findByMenuId(menu.getId());
        assertThat(findPizzas).contains(pizza);

    }
}

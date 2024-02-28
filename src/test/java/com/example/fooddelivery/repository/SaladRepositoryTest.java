package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Pizza;
import com.example.fooddelivery.model.Salad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class SaladRepositoryTest {
    @Autowired
    private SaladRepository saladRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByName(){

        Salad salad =Salad.builder()
                .name("Cezar")
                .build();
        entityManager.persistAndFlush(salad);

        Salad findSalad = saladRepo.findByName(salad.getName());
        assertEquals(salad,findSalad);

    }
    @Test
    public void testDeleteByName(){

        Salad salad =Salad.builder()
                .name("Cezar")
                .build();
        entityManager.persistAndFlush(salad);
        saladRepo.deleteByName(salad.getName());

        Salad findSalad = saladRepo.findByName(salad.getName());
        assertNull(findSalad);

    }
    @Test
    public void testFindByMenuId(){
        Menu menu = Menu.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(menu);

        Salad salad =Salad.builder()
                .name("Cezar")
                .build();
        salad.setMenu(menu);
        entityManager.persistAndFlush(salad);
        entityManager.persistAndFlush(menu);

        List<Salad> findSalads = saladRepo.findByMenuId(menu.getId());
        assertThat(findSalads).contains(salad);

    }
}

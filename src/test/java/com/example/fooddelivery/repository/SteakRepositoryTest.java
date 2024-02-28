package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Salad;
import com.example.fooddelivery.model.Steak;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class SteakRepositoryTest {
    @Autowired
    private SteakRepository steakRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByName(){

        Steak steak =Steak.builder()
                .name("Tartar")
                .build();
        entityManager.persistAndFlush(steak);

        Steak findSteak = steakRepo.findByName(steak.getName());
        assertEquals(steak,findSteak);

    }
    @Test
    public void testDeleteByName(){

        Steak steak =Steak.builder()
                .name("Tartar")
                .build();
        entityManager.persistAndFlush(steak);
        steakRepo.deleteByName(steak.getName());

        Steak findSteak = steakRepo.findByName(steak.getName());
        assertNull(findSteak);

    }
    @Test
    public void testFindByMenuId(){
        Menu menu = Menu.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(menu);

        Steak steak =Steak.builder()
                .name("Tartar")
                .build();
        steak.setMenu(menu);
        entityManager.persistAndFlush(steak);
        entityManager.persistAndFlush(menu);

        List<Steak> findSteaks = steakRepo.findByMenuId(menu.getId());
        assertThat(findSteaks).contains(steak);

    }
}

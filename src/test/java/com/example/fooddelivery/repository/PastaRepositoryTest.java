package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Menu;
import com.example.fooddelivery.model.Pasta;
import com.example.fooddelivery.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PastaRepositoryTest {
    @Autowired
    private PastaRepository pastaRepo;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testFindByName(){

        Pasta pasta =Pasta.builder()
                .name("Bolonese")
                .build();
        entityManager.persistAndFlush(pasta);

        Pasta findPasta = pastaRepo.findByName(pasta.getName());
        assertEquals(pasta,findPasta);

    }
    @Test
    public void testDeleteByName(){

        Pasta pasta =Pasta.builder()
                .name("Bolonese")
                .build();
        entityManager.persistAndFlush(pasta);
        pastaRepo.deleteByName(pasta.getName());

        Pasta findPasta = pastaRepo.findByName(pasta.getName());
        assertNull(findPasta);

    }
    @Test
    public void testFindByMenuId(){
        Menu menu = Menu.builder()
                .name("Happy")
                .build();
        entityManager.persistAndFlush(menu);

        Pasta pasta =Pasta.builder()
                .name("Bolonese")
                .build();
        pasta.setMenu(menu);
        entityManager.persistAndFlush(pasta);
        entityManager.persistAndFlush(menu);

        List<Pasta> findPastas = pastaRepo.findByMenuId(menu.getId());
        assertThat(findPastas).contains(pasta);

    }
}

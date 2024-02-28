package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.DeliveryGuy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByUserName(){

        Customer customer = Customer.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(customer);

        Customer findCustomer = customerRepo.findCustomerByUsername("Ivan");
        assertEquals(customer,findCustomer);

    }
    @Test
    public void testFindByEmail(){

        Customer customer = Customer.builder()
                .email("nikinikolov2002@gmail.com")
                .build();
        entityManager.persistAndFlush(customer);

        Customer findCustomer = customerRepo.findByEmail("nikinikolov2002@gmail.com");
        assertEquals(customer,findCustomer);

    }
    @Test
    public void testFindByTokensIn(){
        List<String>tokens = new ArrayList<>();
        String token = UUID
                .randomUUID()
                .toString();
        tokens.add(token);

        Customer customer = Customer.builder()
                .tokens(tokens)
                .build();
        entityManager.persistAndFlush(customer);

        Customer findCustomer = customerRepo.findByTokensIn(tokens);
        assertEquals(customer,findCustomer);

    }
    @Test
    public void testDeleteByUserName(){


        Customer customer = Customer.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(customer);

       customerRepo.deleteCustomerByUsername("Ivan");
        Customer customerNull = customerRepo.findCustomerByUsername("Ivan");

        assertNull(customerNull);

    }
    @Test
    @Transactional
    public void testFindCustomerByCartDeliveryGuy() {
        List<Cart> carts = new ArrayList<>();
        for (Cart cart : carts){
            cart = Cart.builder()
                    .location("Sofia")
                    .build();
            entityManager.persistAndFlush(cart);
        }

        // Create DeliveryGuy

        for (Cart cart : carts) {
            // Create Customer
            Customer customer = Customer.builder()
                    .username("Ivan")
                    .cart(cart)
                    .build();
            entityManager.persistAndFlush(customer);
            DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                    .id(UUID.randomUUID())
                    .username("Gosho")
                    .build();

            cart.setDeliveryGuy(deliveryGuy);
            cart.setCustomer(customer);
            entityManager.persistAndFlush(cart);
            entityManager.persistAndFlush(deliveryGuy);
        }



        // Load the DeliveryGuy from the cart to associate it with the current session
        for(Cart cart1:carts) {
            DeliveryGuy loadedDeliveryGuy = cart1.getDeliveryGuy();
            Customer findCustomer = customerRepo.findCustomerByCartDeliveryGuy(loadedDeliveryGuy.getId());
            assertEquals("Ivan", findCustomer.getUsername());
        }

        // Actual test


    }

}



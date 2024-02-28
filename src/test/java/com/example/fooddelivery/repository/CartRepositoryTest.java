package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Cart;
import com.example.fooddelivery.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testFindByCustomerUserName(){

        Customer customer = Customer.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(customer);

        Cart cart = Cart.builder()
                .customer(customer)
                .build();
        customer.setCart(cart);

        entityManager.persistAndFlush(cart);
        entityManager.persistAndFlush(customer);


        Cart findCart = cartRepo.findByCustomersUsername(customer.getUsername());
        assertEquals(cart,findCart);

    }

    @Test
    public void testDeleteByCustomerUserName(){

        Customer customer = Customer.builder()
                .username("Ivan")
                .build();
        entityManager.persistAndFlush(customer);

        Cart cart = Cart.builder()
                .customer(customer)
                .build();
        customer.setCart(cart);

        entityManager.persistAndFlush(cart);
        entityManager.persistAndFlush(customer);
        cartRepo.deleteByCustomerUsername(customer.getUsername());

        Cart nullCart = cartRepo.findByCustomersUsername(customer.getUsername());
        assertNull(nullCart);

    }
    @Test
    public void testFindByCustomerId(){
        // Create a new Customer entity
        Customer customer = Customer.builder()
                .username("Ivan")
                .build();

        // Persist and flush the Customer entity
        entityManager.persistAndFlush(customer);

        // Create a new Cart entity associated with the Customer
        Cart cart = Cart.builder()
                .customer(customer)
                .build();

        // Set the Cart for the Customer
        customer.setCart(cart);

        // Persist and flush the Cart entity
        entityManager.persistAndFlush(cart);
        entityManager.persistAndFlush(customer);

        // Retrieve the Cart by customer ID
        Cart findCart = cartRepo.findByCustomersId(customer.getId());

        // Retrieve the associated Customer from the Cart
        Customer customer1 = findCart.getCustomer();

        // Assert the result
        assertEquals("Ivan", customer1.getUsername());
    }

}

package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

}

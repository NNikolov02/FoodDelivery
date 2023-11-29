package com.example.fooddelivery.repository;


import com.example.fooddelivery.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerPagingRepository extends PagingAndSortingRepository<Customer, UUID> {

}

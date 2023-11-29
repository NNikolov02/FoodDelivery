package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.Salad;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaladPagingRepository extends PagingAndSortingRepository<Salad, UUID> {
}

package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.Pasta;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PastaPagingRepository extends PagingAndSortingRepository<Pasta, UUID> {
}

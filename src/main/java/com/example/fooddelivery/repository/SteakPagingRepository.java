package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.Steak;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SteakPagingRepository extends PagingAndSortingRepository<Steak, UUID> {
}

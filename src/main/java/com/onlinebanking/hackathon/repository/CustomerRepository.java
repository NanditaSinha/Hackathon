package com.onlinebanking.hackathon.repository;

import com.onlinebanking.hackathon.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findOptionalByUsername(String username);
    Customer findByUsername(String username);
}


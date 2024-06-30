package com.onlinebanking.hackathon.Service;

import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
}

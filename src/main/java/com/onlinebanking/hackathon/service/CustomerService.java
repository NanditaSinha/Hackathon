package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findByUsername(String username)
    {
        return customerRepository.findByUsername(username);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        if (usernameExists(customer.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        return customerRepository.save(customer);
    }

    public boolean usernameExists(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }


}

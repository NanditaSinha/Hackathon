package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.dto.CustomerMapper;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public boolean usernameExists(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }


    public CustomerDTO getCustomerDTO(Customer customer) {
        return CustomerMapper.toCustomerDTO(customer);
    }

    public Customer getCustomer(CustomerDTO customerDTO) {
        return CustomerMapper.toCustomer(customerDTO);
    }

}

package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.dto.CustomerMapper;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findOptionalByUsername(String username) {
        return customerRepository.findOptionalByUsername(username);
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public CustomerDTO getCustomerDTO(Customer customer) {
        return CustomerMapper.toCustomerDTO(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customerDetail = customerRepository.findOptionalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
        return new CustomerDetails(customerDetail);

    }
}

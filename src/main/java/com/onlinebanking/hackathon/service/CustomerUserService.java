package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.onlinebanking.hackathon.entity.CustomerUser;
import com.onlinebanking.hackathon.repository.CustomerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class CustomerUserService {

    @Autowired
    private CustomerUserRepository customerUserRepository;

    public List<CustomerUser> getAllCustomers() {
        return customerUserRepository.findAll();
    }

    public CustomerUser getCustomerById(Long id) {
        return customerUserRepository.findById(id).orElse(null);
    }

    public CustomerUser saveCustomer(CustomerUser customerUser) {
        return customerUserRepository.save(customerUser);
    }

    public void deleteCustomer(Long id) {
        Optional<CustomerUser> customer = customerUserRepository.findById(id);

        if(customer.isEmpty())
            throw new UserNotFoundException("Customer with id " + id + " not found");

        if(customer==null)
            throw new UserNotFoundException("Customer with id " + id + " not found");
        customerUserRepository.deleteById(id);
    }

}

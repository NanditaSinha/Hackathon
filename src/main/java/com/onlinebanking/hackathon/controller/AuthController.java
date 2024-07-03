package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/loginuser")
    public ResponseEntity<?> loginuser(@RequestBody LoginRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());


        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(request.getPassword())) {
                String accountUrl = "/hackathon/accounts/findAccountByUsername/" + request.getUsername();
                LoginResponse response = new LoginResponse("Login successful", accountUrl);

                return ResponseEntity.ok(response);
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

    }

    @GetMapping(path = "/findcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/findByUsername/{username}")
    public Customer findCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customerOpt = customerService.findByUsername(username);
        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with username " + username + " not found");
        }
        Customer customer = customerOpt.get();
        return customer;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

}





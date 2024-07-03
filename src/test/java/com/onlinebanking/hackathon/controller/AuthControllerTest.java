package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AuthController authController;


    @Test
    void testLoginUserSuccess() {
        LoginRequest request = new LoginRequest("NeelamPrasad", "password123");
        Customer customer = new Customer();
        customer.setUsername("NeelamPrasad");
        customer.setPassword("password123");

        when(customerService.findByUsername("NeelamPrasad")).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = authController.loginuser(request, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals("Login successful", loginResponse.getMessage());
        assertEquals("/hackathon/accounts/findAccountByUsername/NeelamPrasad", loginResponse.getAccountUrl());
    }

    @Test
    void testLoginUserFailure() {
        LoginRequest request = new LoginRequest("NeelamPrasad", "password123");

        when(customerService.findByUsername("NeelamPrasad")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.loginuser(request, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setUsername("NeelamPrasad");
        Customer customer2 = new Customer();
        customer2.setUsername("NanditaSinha");

        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> result = authController.getAllCustomers();

        assertEquals(2, result.size());
        assertEquals("NeelamPrasad", result.get(0).getUsername());
        assertEquals("NanditaSinha", result.get(1).getUsername());
    }

    @Test
    void testFindCustomerByUsernameSuccess() {
        Customer customer = new Customer();
        customer.setUsername("NanditaSinha");

        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.of(customer));

        EntityModel<Customer> response = authController.findCustomerByUsername("NanditaSinha");

        assertEquals("NanditaSinha", response.getContent().getUsername());
    }

    @Test
    void testFindCustomerByUsernameFailure() {
        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.empty());

        try {
            authController.findCustomerByUsername("NanditaSinha");
        } catch (UserNotFoundException e) {
            assertEquals("Customer with username NanditaSinha not found", e.getMessage());
        }
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setUsername("RichaS");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = authController.createCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("RichaS", response.getBody().getUsername());
    }
}

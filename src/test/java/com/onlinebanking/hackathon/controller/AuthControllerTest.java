package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private AuthController authController;
    private Customer customer;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setUsername("NanditaSinha");
        customer.setPassword("Ganesh");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("NanditaSinha");
        loginRequest.setPassword("Ganesh");
    }

    @Test
    void testLoginUserSuccess() {
        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = authController.loginuser(loginRequest, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof LoginResponse);
        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals("Login successful", loginResponse.getMessage());
        assertEquals("/hackathon/accounts/findAccountByUsername/NanditaSinha", loginResponse.getAccountUrl());
    }

    @Test
    void testLoginUserInvalidPassword() {
        customer.setPassword("testing123");
        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = authController.loginuser(loginRequest, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testLoginUserUserNotFound() {
        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.loginuser(loginRequest, UriComponentsBuilder.newInstance());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        assertEquals(1, authController.getAllCustomers().size());
    }

    @Test
    void testFindCustomerByUsernameNotFound() {
        when(customerService.findByUsername("NanditaSinha")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            authController.findCustomerByUsername("NanditaSinha");
        });
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = authController.createCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
}

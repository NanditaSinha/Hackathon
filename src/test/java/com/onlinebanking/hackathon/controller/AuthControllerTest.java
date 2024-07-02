package com.onlinebanking.hackathon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin_Success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("testpass");

        Customer customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("encodedPassword");

        when(customerService.findByUsername("testuser")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("wronguser");
        request.setPassword("wrongpass");

        when(customerService.findByUsername("wronguser")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setUsername("testuser1");

        Customer customer2 = new Customer();
        customer2.setUsername("testuser2");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/auth/findcustomers"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    public void testFindCustomerByUsername_Success() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("testuser");

        when(customerService.findByUsername("testuser")).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/auth/findByUsername/testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EntityModel.of(customer))));
    }

    @Test
    public void testFindCustomerByUsername_NotFound() throws Exception {
        when(customerService.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        mockMvc.perform(get("/auth/findByUsername/nonexistentuser"))
                .andExpect(status().isNotFound());
    }
}

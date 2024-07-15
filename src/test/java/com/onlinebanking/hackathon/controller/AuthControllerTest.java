package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.config.JwtUtil;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private CustomerService customerService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Principal principal;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /* @Test
    public void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("NanditaSinha");
        loginRequest.setPassword("Ganesh@123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("NanditaSinha");
        when(jwtUtil.generateToken("NanditaSinha")).thenReturn("jwt-token");

        ResponseEntity<?> response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        CustomerLoginResponse loginResponse = (CustomerLoginResponse) response.getBody();
        assertNotNull(loginResponse);
        assertEquals("Logged In Successfully", loginResponse.getMessage());
        assertEquals("jwt-token", loginResponse.getToken());
        assertEquals("NanditaSinha", loginResponse.getUsername());
    }*/

    @Test
    public void testGetAllCustomers_Success() {
        authController.getAllCustomers();
        verify(customerService).getAllCustomers();
    }

    @Test
    public void testFindCustomerByUsername_Success() {
        String username = "NanditaSinha";
        when(principal.getName()).thenReturn(username);

        Customer customer = new Customer();
        customer.setUsername(username);
        when(customerService.findOptionalByUsername(username)).thenReturn(Optional.of(customer));
        when(customerService.getCustomerDTO(customer)).thenReturn(new CustomerDTO());

        CustomerDTO result = authController.findCustomerByUsername(principal);

        assertNotNull(result);
        verify(customerService).findOptionalByUsername(username);
        verify(customerService).getCustomerDTO(customer);
    }

    @Test
    public void testFindCustomerByUsername_UserNotFound() {
        String username = "NanditaSinha";
        when(principal.getName()).thenReturn(username);

        when(customerService.findOptionalByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            authController.findCustomerByUsername(principal);
        });

        verify(customerService).findOptionalByUsername(username);
    }
}

package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.config.JwtUtil;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.dto.CustomerLoginResponse;
import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(CustomerService customerService, JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;

    }

    @Operation(summary = "Create Authentication Token with login credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Logged In")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));

        var loginResponse = new CustomerLoginResponse();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(request.getUsername());
            loginResponse.setMessage("Logged In Successfully");
            loginResponse.setToken(token);
            loginResponse.setUsername(userDetails.getUsername());
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/findcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Fetch Customer details based on Authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch Customer details")
    })
    @GetMapping("/findByUsername")
    public CustomerDTO findCustomerByUsername(Principal principal) {
        if(Objects.isNull(principal)) {
            throw new UnauthorizedException("Un-Authorized access");
        }
        String username = principal.getName();
        Optional<Customer> customerOpt = customerService.findOptionalByUsername(username);
        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with username " + username + " not found");
        }

        Customer customer = customerOpt.get();
        CustomerDTO customerDTO = customerService.getCustomerDTO(customer);
        return customerDTO;
    }

}





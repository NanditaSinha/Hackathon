package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.config.JwtUtil;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.dto.CustomerLoginResponse;
import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(CustomerService customerService, JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;

    }

    @Operation(summary = "Get user by ID",
            description = "Returns a user object based on the provided ID",
            responses = {
                    @ApiResponse(description = "Successful Operation", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(description = "User not found", responseCode = "404")
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
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

    @GetMapping("/findByUsername")
    public CustomerDTO findCustomerByUsername(Principal principal) {
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





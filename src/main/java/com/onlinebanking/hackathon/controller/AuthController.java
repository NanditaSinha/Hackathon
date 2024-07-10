package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Get user by ID",
            description = "Returns a user object based on the provided ID",
            responses = {
                    @ApiResponse(description = "Successful Operation", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(description = "User not found", responseCode = "404")
            })
    @PostMapping("/loginuser")
    public ResponseEntity<String> loginuser(@RequestBody LoginRequest request) {
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());


        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
                return ResponseEntity.ok("Login successful");
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
    public CustomerDTO findCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customerOpt = customerService.findByUsername(username);
        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with username " + username + " not found");
        }

        Customer customer = customerOpt.get();
        CustomerDTO customerDTO = customerService.getCustomerDTO(customer);
        return customerDTO;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerDTO customerDTO = customerService.getCustomerDTO(createdCustomer);
        return ResponseEntity.ok(customerDTO);
    }

}





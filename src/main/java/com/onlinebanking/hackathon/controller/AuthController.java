package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginRequest;
import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;
    
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());

        if (customerOpt.isPresent() && passwordEncoder.matches(request.getPassword(), customerOpt.get().getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());

        if (customerOpt.isPresent()) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/loginuser")
    public ResponseEntity<?> loginuser(@RequestBody LoginRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());


        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword().equals(request.getPassword()))  {
                String accountUrl = "/hackathon/accounts/findAccountByUsername/" + request.getUsername();
                LoginResponse response = new LoginResponse("Login successful", accountUrl);

                /*String accountUrl = uriComponentsBuilder.path("/accounts/findAccountByUsername/{username}")
                        .buildAndExpand(request.getUsername())
                        .toUriString();

                LoginResponseAccount response = new LoginResponseAccount("Login successful", accountUrl);
                ResponseEntity<String> accountResponse = restTemplate.getForEntity(accountUrl, String.class);
                if (accountResponse.getStatusCode() == HttpStatus.OK) {
                    String accountDetails = accountResponse.getBody();
                    response.setAccountDetails(accountDetails); // Assuming LoginResponse has an accountDetails field
                }*/
                
                return ResponseEntity.ok().body(response);
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        }return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

    }

    @GetMapping(path = "/findcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/findByUsername/{username}")
    public EntityModel<Customer> findCustomerByUsername(@PathVariable String username) {

        Optional<Customer> customerOpt = customerService.findByUsername(username);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with username " + username + " not found");
        }

        Customer customer = customerOpt.get();
        EntityModel<Customer> entityModel = EntityModel.of(customer);

       /* WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCustomers());
        entityModel.add(link.withRel("all-customers"));*/

        return entityModel;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

}





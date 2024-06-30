package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> loginuser(@RequestBody LoginRequest request) {
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());


        if (customerOpt.isPresent()) {
            return ResponseEntity.ok().body("Login successful");
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
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

}

@Data
class LoginRequest {
    private String username;
    private String password;
}

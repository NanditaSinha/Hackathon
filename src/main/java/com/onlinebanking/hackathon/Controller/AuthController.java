package com.onlinebanking.hackathon.Controller;

import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Service.CustomerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<Customer> customerOpt = customerService.findByUsername(request.getUsername());

        if (customerOpt.isPresent() && passwordEncoder.matches(request.getPassword(), customerOpt.get().getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
}

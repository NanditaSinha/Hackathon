package com.onlinebanking.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @NotBlank(message = "First Name is mandatory")
    private String firstname;

    private String lastname;

    @Size(min = 5, message = "Password should have at least 5 characters")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long phone;


}

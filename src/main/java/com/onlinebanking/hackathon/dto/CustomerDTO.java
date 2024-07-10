package com.onlinebanking.hackathon.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private long phone;
}

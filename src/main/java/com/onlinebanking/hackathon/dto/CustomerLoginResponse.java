package com.onlinebanking.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoginResponse {
    private String message;
    private String username;
    private String token;
    private CustomerDTO customerDetails;
}

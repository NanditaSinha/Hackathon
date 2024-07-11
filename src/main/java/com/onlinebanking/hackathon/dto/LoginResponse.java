package com.onlinebanking.hackathon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class LoginResponse {
    private String message;
    private String accountUrl;

}


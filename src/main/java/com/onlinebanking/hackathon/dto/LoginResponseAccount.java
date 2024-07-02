package com.onlinebanking.hackathon.dto;

import lombok.Data;

@Data
public class LoginResponseAccount {
    private String message;
    private String accountUrl;
    private String accountDetails; // Added to hold account details if fetched

    public LoginResponseAccount(String message, String accountUrl) {
        this.message = message;
        this.accountUrl = accountUrl;
    }
}
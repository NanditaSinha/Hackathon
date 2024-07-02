package com.onlinebanking.hackathon.dto;

import lombok.Data;

public class LoginResponse {
    private String message;
    private String accountUrl;

    public LoginResponse(String message, String accountUrl) {
        this.message = message;
        this.accountUrl = accountUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }
}


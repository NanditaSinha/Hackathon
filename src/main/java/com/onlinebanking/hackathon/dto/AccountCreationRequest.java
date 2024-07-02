package com.onlinebanking.hackathon.dto;

import java.math.BigDecimal;

public class AccountCreationRequest {
    private Long accountNumber;
    private BigDecimal balance;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
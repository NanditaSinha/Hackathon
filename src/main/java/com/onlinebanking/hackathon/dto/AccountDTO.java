package com.onlinebanking.hackathon.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long accountId;
    private Long accountNumber;
    private BigDecimal balance;
    private String customerUsername;
    private String customerFirstname;
    private String customerLastname;
    private String customerEmail;
    private long customerPhone;
}

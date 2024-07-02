package com.onlinebanking.hackathon.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestByAccountNumber {

    private Long fromAccountNumber;
    private Long toAccountNumber;
    private BigDecimal amount;
    private String comment;

}

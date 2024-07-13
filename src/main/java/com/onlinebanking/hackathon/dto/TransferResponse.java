package com.onlinebanking.hackathon.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TransferResponse {
    private String message;
    private AccountDTO accountDetails;
}
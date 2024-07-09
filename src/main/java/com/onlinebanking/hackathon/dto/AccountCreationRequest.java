package com.onlinebanking.hackathon.dto;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class AccountCreationRequest {
    private Long accountNumber;
    private BigDecimal balance;

}
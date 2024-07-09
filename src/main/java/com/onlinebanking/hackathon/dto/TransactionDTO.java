package com.onlinebanking.hackathon.dto;
import com.onlinebanking.hackathon.entity.Account;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String comment;
    private String typeoftransaction;

}

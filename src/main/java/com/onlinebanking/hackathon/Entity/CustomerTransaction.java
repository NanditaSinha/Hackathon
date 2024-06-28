package com.onlinebanking.hackathon.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
public class CustomerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String transactionType;
    private BigDecimal amount;
    private Timestamp transactionDate;
    private String description;

    // getters and setters

}

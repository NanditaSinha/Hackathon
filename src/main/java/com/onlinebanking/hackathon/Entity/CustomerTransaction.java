package com.onlinebanking.hackathon.Entity;

import com.onlinebanking.hackathon.enums.AccountActivityType;
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
    @JoinColumn(name = "account_id_from", nullable = false)
    private Account accountto;

    @ManyToOne
    @JoinColumn(name = "account_id_to", nullable = false)
    private Account accountfrom;

    private String transactionType;
    private BigDecimal amount;

    private Timestamp dateofTransfer;
    private Timestamp transactionDate;
    private String comment;

    private AccountActivityType accountActivityType;




    // getters and setters

}

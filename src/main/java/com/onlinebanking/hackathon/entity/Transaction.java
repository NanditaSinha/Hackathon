package com.onlinebanking.hackathon.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    private BigDecimal amount;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;

    private String comment;

    public Transaction() {
    }

    public Transaction(Long id, Account fromAccount, Account toAccount, BigDecimal amount, LocalDateTime  transactionDate, String comment) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.comment = comment;
    }

}

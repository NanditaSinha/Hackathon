package com.onlinebanking.hackathon.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Accountid",nullable = false)
    private Long accountId;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private String accountType;
    private BigDecimal balance;

    // getters and setters

}

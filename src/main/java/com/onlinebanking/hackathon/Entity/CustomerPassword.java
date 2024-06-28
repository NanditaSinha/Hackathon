package com.onlinebanking.hackathon.Entity;

import jakarta.persistence.*;

@Entity
public class CustomerPassword {
    @Id
    private Long customerId;

    private String passwordHash;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

}

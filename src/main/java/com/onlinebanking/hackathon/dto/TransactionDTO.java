package com.onlinebanking.hackathon.dto;

import com.onlinebanking.hackathon.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String comment;
    private String typeoftransaction;

    public TransactionDTO(Transaction transaction, String typeoftransaction) {
        this.id = transaction.getId();
        this.fromAccountNumber = transaction.getFromAccount().getAccountNumber();
        this.toAccountNumber = transaction.getToAccount().getAccountNumber();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.comment = transaction.getComment();
        this.typeoftransaction = typeoftransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTypeoftransaction() {
        return typeoftransaction;
    }

    public void setTypeoftransaction(String typeoftransaction) {
        this.typeoftransaction = typeoftransaction;
    }

    // Getters and Setters
}

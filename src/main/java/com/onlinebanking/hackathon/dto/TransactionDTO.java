package com.onlinebanking.hackathon.dto;
import com.onlinebanking.hackathon.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String comment;
    private String typeoftransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
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
}

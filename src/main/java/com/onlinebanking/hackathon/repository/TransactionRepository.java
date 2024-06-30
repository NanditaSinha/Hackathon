package com.onlinebanking.hackathon.repository;

import com.onlinebanking.hackathon.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(String accountNumber);
}

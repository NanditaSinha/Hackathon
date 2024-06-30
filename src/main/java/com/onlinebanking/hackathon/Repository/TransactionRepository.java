package com.onlinebanking.hackathon.Repository;

import com.onlinebanking.hackathon.Entity.Account;
import com.onlinebanking.hackathon.Entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(String accountNumber);
}

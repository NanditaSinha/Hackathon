package com.onlinebanking.hackathon.repository;

import com.onlinebanking.hackathon.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(Long accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = :accountNumber ORDER BY t.transactionDate DESC")
    List<Transaction> findLast10TransactionsByAccountNumber(@Param("accountNumber") Long accountNumber);

}

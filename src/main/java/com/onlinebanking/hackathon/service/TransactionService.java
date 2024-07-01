package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.repository.AccountRepository;
import com.onlinebanking.hackathon.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void transferFunds(Long fromAccountId, Long toAccountId, BigDecimal amount, String comment) {
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setComment(comment);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<Transaction> findLast10TransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(accountNumber);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

/*    public Account getAccountDetails(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }*/

    public List<TransactionDTO> getLast10TransactionsWithType(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findLast10TransactionsByAccountNumber(accountNumber, PageRequest.of(0, 10));
        return transactions.stream().map(transaction -> {
            String typeoftransaction = transaction.getFromAccount().getAccountNumber().equals(accountNumber) ? "DEBIT" : "CREDIT";
            return new TransactionDTO(transaction, typeoftransaction);
        }).collect(Collectors.toList());
    }


}


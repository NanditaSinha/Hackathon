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

        if (fromAccountId == toAccountId) {
            throw new RuntimeException("From Account and To Account cannot be same");
        }
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



    public List<Transaction> findLast10TransactionsByAccountNumber(Long accountNumber) {
        return transactionRepository.findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(accountNumber);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

/*    public Account getAccountDetails(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }*/

    public List<TransactionDTO> getLast10Transactions(Long accountNumber) {
        List<Transaction> transactions = transactionRepository.findLast10TransactionsByAccountNumber(accountNumber);
        return transactions.stream().limit(10).map(transaction -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setFromAccount(transaction.getFromAccount());
            dto.setToAccount(transaction.getToAccount());
            dto.setAmount(transaction.getAmount());
            dto.setTransactionDate(transaction.getTransactionDate());
            dto.setComment(transaction.getComment());
            if (transaction.getFromAccount().getAccountNumber().equals(accountNumber)) {
                dto.setTypeoftransaction("DEBIT");
            } else if (transaction.getToAccount().getAccountNumber().equals(accountNumber)) {
                dto.setTypeoftransaction("CREDIT");
            }
            return dto;
        }).collect(Collectors.toList());
    }
}


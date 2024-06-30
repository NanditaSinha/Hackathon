package com.onlinebanking.hackathon.Service;

import com.onlinebanking.hackathon.Entity.Account;
import com.onlinebanking.hackathon.Entity.CustomerUser;
import com.onlinebanking.hackathon.Entity.Transaction;
import com.onlinebanking.hackathon.Repository.AccountRepository;
import com.onlinebanking.hackathon.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        transactionRepository.save(transaction);
    }

    public List<Transaction> findLast10TransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findFirst10ByFromAccount_AccountNumberOrderByTransactionDateDesc(accountNumber);
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }
}

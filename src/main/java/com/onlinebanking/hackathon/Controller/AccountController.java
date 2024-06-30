package com.onlinebanking.hackathon.Controller;

import com.onlinebanking.hackathon.Entity.Account;
import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Entity.Transaction;
import com.onlinebanking.hackathon.Service.AccountService;
import com.onlinebanking.hackathon.Service.CustomerService;
import com.onlinebanking.hackathon.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Account> getAccounts(Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Customer not found"));
        return accountService.findByCustomer(customer);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getRecentTransactions(@PathVariable Long id, @RequestParam(defaultValue = "10") int limit) {
        Account account = accountService.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionService.findRecentTransactions(account, limit);
    }
}

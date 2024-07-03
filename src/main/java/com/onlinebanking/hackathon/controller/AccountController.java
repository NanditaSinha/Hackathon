package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
import com.onlinebanking.hackathon.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/findAccountByCustomerId/{id}")
    public List<Account> findAccountByCustomerId(@PathVariable long id) {
        Optional<Customer> customerOpt = customerService.findById(id);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with id " + id + " not found");
        }

        Customer customer = customerOpt.get();
        List<Account> accounts = accountService.findByCustomer(customer);
        return accounts;
    }

    @GetMapping("/findAccountByUsername/{username}")
    public List<Account> findAccountByCustomerId(@PathVariable String username) {
        Optional<Customer> customerOpt = customerService.findByUsername(username);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with id " + username + " not found");
        }

        Customer customer = customerOpt.get();
        List<Account> accounts = accountService.findByCustomer(customer);

        return accounts;
    }

    @GetMapping("/accountdetail/{accountNumber}")
    public Account getAccountByAccountNumber(@PathVariable Long accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return account;
    }

    @PostMapping("/createaccountBycustomerid/{customerId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long customerId, @Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account, customerId);
        if (createdAccount == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/createaccountByusername/{username}")
    public ResponseEntity<Account> createAccountByUserName(@PathVariable String username, @Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccountUserName(username, account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/findAccountsByFromAccountNumber/{fromAccountNumber}")
    public List<Account> findAccountsByFromAccountNumber(@PathVariable Long fromAccountNumber) {
        Optional<Account> accountOpt = accountService.findByAccountNumber(fromAccountNumber);

        if (!accountOpt.isPresent()) {
            throw new UserNotFoundException("Account with number " + fromAccountNumber + " not found");
        }

        Account account = accountOpt.get();
        Customer customer = account.getCustomer();
        List<Account> accounts = accountService.findByCustomer(customer);

        return accounts;
    }

}

package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.dto.*;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.repository.AccountRepository;
import com.onlinebanking.hackathon.repository.CustomerRepository;
import com.onlinebanking.hackathon.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountMapper accountMapper;

      @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AccountDTO> findByCustomer(Customer customer) {
        List<Account> accounts = accountRepository.findByCustomer(customer);
        return accounts.stream()
                .map(this::getAccountDTO)
                .collect(Collectors.toList());

    }

    public Optional<Account> findByAccountNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account createAccountUserName(String username, Account accountDetails) {

        Optional<Customer> customerOptional = customerRepository.findOptionalByUsername(username);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found with this user name: " + username);
        }
        Customer customer = customerOptional.get();
        accountDetails.setCustomer(customer);
        return accountRepository.save(accountDetails);
    }

    public Account createAccount(Account account, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            account.setCustomer(customer);
            return accountRepository.save(account);
        }
        return null;
    }

    @Transactional
    public void transferFundsfromAccount(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount, String comment) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount == toAccount) {
            throw new RuntimeException("From Account and To Account cannot be same");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds in the source account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        Transaction transaction = new Transaction(null, fromAccount, toAccount, amount, LocalDateTime.now(), comment);
        transactionRepository.save(transaction);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public AccountDTO getAccountDTO(Account account) {

        return AccountMapper.convertToDto(account);
    }

    public boolean usernameExists(String username) {
        return customerRepository.findOptionalByUsername(username).isPresent();
    }

    public Customer createCustomer(Customer customer) {
        if (usernameExists(customer.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
    public CustomerDTO getCustomerDTO(Customer customer) {
        return CustomerMapper.toCustomerDTO(customer);
    }

    public boolean isAccountBelongToCustomer(String username, Long accountNumber) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            return false;
        }
        Account account = accountRepository.findByAccountNumberAndCustomer(accountNumber, customer);
        return account != null;
    }

}

package com.onlinebanking.hackathon.service;

import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.repository.AccountRepository;
import com.onlinebanking.hackathon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public List<Account> findByCustomer(Customer customer) {
        return accountRepository.findByCustomer(customer);
    }

    public List<Account> findByCustomerId(Long customerId) {
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return accountRepository.findByCustomer(customer);
    }

    public List<Account> findByCustomerUsername(String username) {
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("Customer not found"));
        return accountRepository.findByCustomer(customer);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Optional<Account> findById(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account getAccountDetails(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account createAccount(Account account, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer != null) {
            account.setCustomer(customer);
            return accountRepository.save(account);
        }
        return null;
    }

    public Account createAccountUserName(String username, Account accountDetails) {

        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        Customer customer = customerOptional.get();
        accountDetails.setCustomer(customer);
        return accountRepository.save(accountDetails);
    }


}

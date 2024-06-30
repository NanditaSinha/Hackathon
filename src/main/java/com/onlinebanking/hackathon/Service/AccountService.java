package com.onlinebanking.hackathon.Service;

import com.onlinebanking.hackathon.Entity.Account;
import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findByCustomer(Customer customer) {
        return accountRepository.findByCustomer(customer);
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
}

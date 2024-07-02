package com.onlinebanking.hackathon.repository;

import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomer(Customer customer);
    Optional<Account> findByAccountNumber(Long accountNumber);
    Optional<Account> findAccountByCustomer_Username(String username);
}
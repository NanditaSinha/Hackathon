package com.onlinebanking.hackathon.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountController accountController;

    private Customer testCustomer;
    private Account testAccount;
    private Principal principal;

    @BeforeEach
    public void setUp() {
        testCustomer = new Customer(1L, "NeelamPrasad", "Neelam", "Prasad", "password123","neelam.prasad@gmail.com",637383952);
        testAccount = new Account(1L, new Customer(1L, "NeelamPrasad", "Neelam", "Prasad", "password123","neelam.prasad@gmail.com",637383952), "ACC001", BigDecimal.valueOf(500));
        principal = () -> "testuser";
    }

    @Test
    public void testFindAccountByCustomerId() {
        when(customerService.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(testAccount);
        when(accountService.findByCustomer(testCustomer)).thenReturn(mockAccounts);

        CollectionModel<EntityModel<Account>> result = accountController.findAccountByCustomerId(testCustomer.getId());

        assertNotNull(result);
        assertEquals(mockAccounts.size(), result.getContent().size());
        verify(customerService, times(1)).findById(testCustomer.getId());
        verify(accountService, times(1)).findByCustomer(testCustomer);
    }

    @Test
    public void testFindAccountByUsername() {
        when(customerService.findByUsername(testCustomer.getUsername())).thenReturn(Optional.of(testCustomer));
        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(testAccount);
        when(accountService.findByCustomer(testCustomer)).thenReturn(mockAccounts);

        CollectionModel<EntityModel<Account>> result = accountController.findAccountByCustomerId(testCustomer.getUsername());

        assertNotNull(result);
        assertEquals(mockAccounts.size(), result.getContent().size());
        verify(customerService, times(1)).findByUsername(testCustomer.getUsername());
        verify(accountService, times(1)).findByCustomer(testCustomer);
    }

    @Test
    public void testGetAccountByAccountNumber() {
        String accountNumber = "acc1";
        when(accountService.findByAccountNumber(accountNumber)).thenReturn(Optional.of(testAccount));

        EntityModel<Account> result = accountController.getAccountByAccountNumber(accountNumber);

        assertNotNull(result);
        assertEquals(testAccount, result.getContent());
        verify(accountService, times(1)).findByAccountNumber(accountNumber);
    }

}


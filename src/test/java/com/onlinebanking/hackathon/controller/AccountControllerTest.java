package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.AccountDTO;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAccountByCustomerId_Success() {
        String username = "NanditaSinha";
        Customer customer = new Customer();
        List<AccountDTO> accountDTOs = Arrays.asList(new AccountDTO());

        when(principal.getName()).thenReturn(username);
        when(customerService.findOptionalByUsername(username)).thenReturn(Optional.of(customer));
        when(accountService.findByCustomer(customer)).thenReturn(accountDTOs);

        List<AccountDTO> result = accountController.findAccountByCustomerId(principal);

        assertNotNull(result);
        assertEquals(accountDTOs.size(), result.size());
        verify(customerService).findOptionalByUsername(username);
        verify(accountService).findByCustomer(customer);
    }

    @Test
    public void testFindAccountByCustomerId_UserNotFound() {
        String username = "NanditaSinha";

        when(principal.getName()).thenReturn(username);
        when(customerService.findOptionalByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> accountController.findAccountByCustomerId(principal));
        verify(customerService).findOptionalByUsername(username);
        verify(accountService, never()).findByCustomer(any(Customer.class));
    }

    @Test
    public void testGetAccountByAccountNumber_Success() {
        String username = "NanditaSinha";
        Long accountNumber = 123L;
        Account account = new Account();
        AccountDTO accountDTO = new AccountDTO();

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToCustomer(username, accountNumber)).thenReturn(true);
        when(accountService.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(accountService.getAccountDTO(account)).thenReturn(accountDTO);

        AccountDTO result = accountController.getAccountByAccountNumber(accountNumber, principal);

        assertNotNull(result);
        assertEquals(accountDTO, result);
        verify(accountService).isAccountBelongToCustomer(username, accountNumber);
        verify(accountService).findByAccountNumber(accountNumber);
        verify(accountService).getAccountDTO(account);
    }

    @Test
    public void testGetAccountByAccountNumber_Unauthorized() {
        String username = "NanditaSinha";
        Long accountNumber = 10007L;

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToCustomer(username, accountNumber)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> accountController.getAccountByAccountNumber(accountNumber, principal));
        verify(accountService).isAccountBelongToCustomer(username, accountNumber);
        verify(accountService, never()).findByAccountNumber(accountNumber);
        verify(accountService, never()).getAccountDTO(any(Account.class));
    }

    @Test
    public void testCreateCustomer_Success() {
        Customer customer = new Customer();
        customer.setUsername("RichaS");
        customer.setFirstname("Richa");
        customer.setLastname("Shukla");
        customer.setEmail("Richa133@gmail.com");
        customer.setPhone( 433465879);
        Customer createdCustomer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();

        when(accountService.createCustomer(customer)).thenReturn(createdCustomer);
        when(accountService.getCustomerDTO(createdCustomer)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = accountController.createCustomer(customer);

        assertNotNull(response);
        assertEquals(customerDTO, response.getBody());
        verify(accountService).createCustomer(customer);
        verify(accountService).getCustomerDTO(createdCustomer);
    }
}

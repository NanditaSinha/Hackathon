package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testFindAccountByCustomerIdSuccess() throws Exception {
        long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        Account account = new Account();
        account.setCustomer(customer);
        List<Account> accounts = List.of(account);

        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountService.findByCustomer(customer)).thenReturn(accounts);

        mockMvc.perform(get("/accounts/findAccountByCustomerId/{id}", customerId))
                .andExpect(status().isOk());

        verify(customerService).findById(customerId);
        verify(accountService).findByCustomer(customer);
    }

    @Test
    void testFindAccountByCustomerIdNotFound() throws Exception {
        long customerId = 1L;

        when(customerService.findById(customerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/findAccountByCustomerId/{id}", customerId))
                .andExpect(status().isNotFound());

        verify(customerService).findById(customerId);
    }

    @Test
    void testFindAccountByUsernameSuccess() throws Exception {
        String username = "NanditaSinha";
        Customer customer = new Customer();
        customer.setUsername(username);
        Account account = new Account();
        account.setCustomer(customer);
        List<Account> accounts = List.of(account);

        when(customerService.findByUsername(username)).thenReturn(Optional.of(customer));
        when(accountService.findByCustomer(customer)).thenReturn(accounts);

        mockMvc.perform(get("/accounts/findAccountByUsername/{username}", username))
                .andExpect(status().isOk());

        verify(customerService).findByUsername(username);
        verify(accountService).findByCustomer(customer);
    }

    @Test
    void testFindAccountByUsernameNotFound() throws Exception {
        String username = "NanditaSinha";

        when(customerService.findByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/findAccountByUsername/{username}", username))
                .andExpect(status().isNotFound());

        verify(customerService).findByUsername(username);
    }

    @Test
    void testGetAccountByAccountNumberSuccess() throws Exception {
        long accountNumber = 12345L;
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountService.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/accounts/accountdetail/{accountNumber}", accountNumber))
                .andExpect(status().isOk());

        verify(accountService).findByAccountNumber(accountNumber);
    }

    @Test
    void testCreateAccountByCustomerIdSuccess() throws Exception {
        long customerId = 1L;
        Account account = new Account();
        account.setCustomer(new Customer());

        when(accountService.createAccount(any(Account.class), eq(customerId))).thenReturn(account);

        mockMvc.perform(post("/accounts/createaccountBycustomerid/{customerId}", customerId)
                        .contentType("application/json")
                        .content("{\"accountNumber\": 12345}"))
                .andExpect(status().isOk());

        verify(accountService).createAccount(any(Account.class), eq(customerId));
    }

  /*  @Test
    void testCreateAccountByCustomerIdNotFound() throws Exception {
        long customerId = 1L;

        when(accountService.createAccount(any(Account.class), eq(customerId))).thenReturn(null);

        mockMvc.perform(post("/accounts/createaccountBycustomerid/{customerId}", customerId)
                        .contentType("application/json")
                        .content("{\"accountNumber\": 12345}"))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).createAccount(any(Account.class), eq(customerId));
    }*/

    @Test
    void testCreateAccountByUsernameSuccess() throws Exception {
        String username = "NanditaSinha";
        Account account = new Account();

        when(accountService.createAccountUserName(eq(username), any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/accounts/createaccountByusername/{username}", username)
                        .contentType("application/json")
                        .content("{\"accountNumber\": 12345}"))
                .andExpect(status().isOk());

        verify(accountService).createAccountUserName(eq(username), any(Account.class));
    }

    @Test
    void testFindAccountsByFromAccountNumberSuccess() throws Exception {
        long fromAccountNumber = 12345L;
        Account account = new Account();
        Customer customer = new Customer();
        account.setCustomer(customer);
        List<Account> accounts = List.of(account);

        when(accountService.findByAccountNumber(fromAccountNumber)).thenReturn(Optional.of(account));
        when(accountService.findByCustomer(customer)).thenReturn(accounts);

        mockMvc.perform(get("/accounts/findAccountsByFromAccountNumber/{fromAccountNumber}", fromAccountNumber))
                .andExpect(status().isOk());

        verify(accountService).findByAccountNumber(fromAccountNumber);
        verify(accountService).findByCustomer(customer);
    }

    @Test
    void testFindAccountsByFromAccountNumberNotFound() throws Exception {
        long fromAccountNumber = 12345L;

        when(accountService.findByAccountNumber(fromAccountNumber)).thenReturn(Optional.empty());

        mockMvc.perform(get("/accounts/findAccountsByFromAccountNumber/{fromAccountNumber}", fromAccountNumber))
                .andExpect(status().isNotFound());

        verify(accountService).findByAccountNumber(fromAccountNumber);
    }
}

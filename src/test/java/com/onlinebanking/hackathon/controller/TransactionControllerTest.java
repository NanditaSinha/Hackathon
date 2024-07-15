package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.AccountDTO;
import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.dto.TransferResponse;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private Principal principal;

    private TransferRequestByAccountNumber transferRequest;

    @BeforeEach
    void setUp() {
        transferRequest = new TransferRequestByAccountNumber();
        transferRequest.setFromAccountNumber(100007L);
        transferRequest.setToAccountNumber(100008L);
        transferRequest.setAmount(BigDecimal.valueOf( 100.0));
        transferRequest.setComment("Test transfer");
    }

    @Test
    void transferFunds_Unauthorized() {
        when(principal.getName()).thenReturn("NanditaSinha");
        when(accountService.isAccountBelongToCustomer("NanditaSinha", 100007L)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            transactionController.transferFunds(principal, transferRequest);
        });
    }

    @Test
    void transferFunds_Success() {
        when(principal.getName()).thenReturn("NanditaSinha");
        when(accountService.isAccountBelongToCustomer("NanditaSinha", 100007L)).thenReturn(true);
        doNothing().when(accountService).transferFundsfromAccount(100007L, 100008L, BigDecimal.valueOf(100.0), "Test transfer");

        Account account = new Account();
        when(accountService.findByAccountNumber(100007L)).thenReturn(Optional.of(account));

        AccountDTO accountDTO = new AccountDTO();
        when(accountService.getAccountDTO(any(Account.class))).thenReturn(accountDTO);

        ResponseEntity<TransferResponse> response = transactionController.transferFunds(principal, transferRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Transfer from Account number " + transferRequest.getFromAccountNumber() + " to Account number: " + transferRequest.getToAccountNumber() + " Successful",  response.getBody().getMessage());
       //assertEquals("Transfer from Account number 1 to Account number: 2 Successful", response.getBody().getMessage());
        assertEquals(accountDTO, response.getBody().getAccountDetails());
    }

 /*   @Test
    void getLast10TransactionsbyAccountnumber_Unauthorized() {
        when(principal.getName()).thenReturn("NanditaSinha");
        when(accountService.isAccountBelongToCustomer("NeelamPrasad", 100008L)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            transactionController.getLast10TransactionsbyAccountnumber(principal, 100007L);
        });
    }*/

    @Test
    void getLast10TransactionsbyAccountnumber_Success() {
        when(principal.getName()).thenReturn("NanditaSinha");
        when(accountService.isAccountBelongToCustomer("NanditaSinha", 100007L)).thenReturn(true);

        List<TransactionDTO> transactions = Arrays.asList(new TransactionDTO(), new TransactionDTO());
        when(transactionService.getLast10Transactions(100007L)).thenReturn(transactions);

        ResponseEntity<List<TransactionDTO>> response = transactionController.getLast10TransactionsbyAccountnumber(principal, 100007L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transactions, response.getBody());
    }
}

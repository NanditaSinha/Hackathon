package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferFunds_Success() {
        String username = "NanditaSinha";
        TransferRequestByAccountNumber request = new TransferRequestByAccountNumber();
        request.setFromAccountNumber(100007L);
        request.setToAccountNumber(67890L);
        request.setAmount(BigDecimal.valueOf( 100.0));
        request.setComment("Test Transfer");

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToUser(username, request.getFromAccountNumber())).thenReturn(true);

        ResponseEntity<String> response = transactionController.transferFunds(principal, request);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok().body("Transfer Successful"), response);

        verify(accountService, times(1)).isAccountBelongToUser(username, request.getFromAccountNumber());
        verify(accountService, times(1)).transferFundsfromAccount(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount(), request.getComment());
    }

    @Test
    public void testTransferFunds_Unauthorized() {
        String username = "NanditaSinha";
        TransferRequestByAccountNumber request = new TransferRequestByAccountNumber();
        request.setFromAccountNumber(100007L);
        request.setToAccountNumber(67890L);
        request.setAmount(BigDecimal.valueOf(100.0));
        request.setComment("Test Transfer");

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToUser(username, request.getFromAccountNumber())).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            transactionController.transferFunds(principal, request);
        });

        verify(accountService, times(1)).isAccountBelongToUser(username, request.getFromAccountNumber());
        verify(accountService, never()).transferFundsfromAccount(123456789L, 987654321L, BigDecimal.valueOf(100.00), "Test by account number");
    }

    @Test
    public void testGetLast10TransactionsByAccountNumber_Success() {
        String username = "NanditaSinha";
        Long accountNumber = 100007L;
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(new TransactionDTO());

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToUser(username, accountNumber)).thenReturn(true);
        when(transactionService.getLast10Transactions(accountNumber)).thenReturn(transactionDTOList);

        ResponseEntity<List<TransactionDTO>> response = transactionController.getLast10TransactionsbyAccountnumber(principal, accountNumber);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok(transactionDTOList), response);

        verify(accountService, times(1)).isAccountBelongToUser(username, accountNumber);
        verify(transactionService, times(1)).getLast10Transactions(accountNumber);
    }

    @Test
    public void testGetLast10TransactionsByAccountNumber_Unauthorized() {
        String username = "testuser";
        Long accountNumber = 100007L;

        when(principal.getName()).thenReturn(username);
        when(accountService.isAccountBelongToUser(username, accountNumber)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            transactionController.getLast10TransactionsbyAccountnumber(principal, accountNumber);
        });

        verify(accountService, times(1)).isAccountBelongToUser(username, accountNumber);
        verify(transactionService, never()).getLast10Transactions(anyLong());
    }
}

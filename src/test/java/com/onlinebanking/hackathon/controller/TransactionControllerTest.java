package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequest;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   /* @Test
    void testTransferFunds() {
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(BigDecimal.valueOf( 100.0));
        request.setComment("Test transfer");

        doNothing().when(transactionService).transferFunds(1L, 2L, BigDecimal.valueOf(100.00), "Test transfer");

        ResponseEntity<?> response = transactionController.transferFunds(request);

        assertEquals("Transfer successful", response.getBody());
        verify(transactionService, times(1)).transferFunds(1L, 2L,BigDecimal.valueOf( 100.00), "Test transfer");
    }*/

    @Test
    void testTransferFundsByAccountNumber() {
        TransferRequestByAccountNumber request = new TransferRequestByAccountNumber();
        request.setFromAccountNumber(123456789L);
        request.setToAccountNumber(987654321L);
        request.setAmount(BigDecimal.valueOf(100.0));
        request.setComment("Test by account number");

        doNothing().when(accountService).transferFundsfromAccount(123456789L, 987654321L, BigDecimal.valueOf( 100.00), "Test by account number");

        ResponseEntity<?> response = transactionController.transferFunds(request);

        assertEquals("Transfer Successful", ((LoginResponse) response.getBody()).getMessage());
        verify(accountService, times(1)).transferFundsfromAccount(123456789L, 987654321L, BigDecimal.valueOf( 100.00), "Test by account number");
    }

    @Test
    void testGetLast10TransactionsByAccountNumber() {
        Long accountNumber = 123456789L;
        List<TransactionDTO> transactionDTOList = Collections.emptyList();

        when(transactionService.getLast10Transactions(accountNumber)).thenReturn(transactionDTOList);

        ResponseEntity<List<TransactionDTO>> response = transactionController.getLast10TransactionsbyAccountnumber(accountNumber);

        assertEquals(transactionDTOList, response.getBody());
        verify(transactionService, times(1)).getLast10Transactions(accountNumber);
    }

    @Test
    void testTransactionDetails() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();

        when(transactionService.findById(transactionId)).thenReturn(transaction);

        Transaction result = transactionController.transactionDetails(transactionId);

        assertEquals(transaction, result);
        verify(transactionService, times(1)).findById(transactionId);
    }
}

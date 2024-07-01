package com.onlinebanking.hackathon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

   /* @Test
    public void testTransferFunds() throws Exception {
        TransferRequest request = new TransferRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal("100.00"));
        request.setComment("Test transfer");

        Mockito.doNothing().when(transactionService).transferFunds(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount(),
                request.getComment()
        );

        mockMvc.perform(post("/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successful"));
    }*/

   /* @Test
    public void testGetLast10Transactions() throws Exception {
        String accountNumber = "12345";
        List<Transaction> transactions = Collections.emptyList(); // Mock the return value

        Mockito.when(transactionService.findLast10TransactionsByAccountNumber(accountNumber)).thenReturn(transactions);

        MvcResult result = mockMvc.perform(get("/transactions/last10/{accountNumber}", accountNumber))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        // Add assertions as needed
    }*/

/*    @Test
    public void testTransactionDetails() throws Exception {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setTransactionDate(LocalDateTime.now());

        Mockito.when(transactionService.findById(transactionId)).thenReturn(transaction);

        mockMvc.perform(get("/transactions/transactionDetails/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionId));
    }*/
}

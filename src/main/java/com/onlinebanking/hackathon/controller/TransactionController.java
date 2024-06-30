package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.Entity.Transaction;
import com.onlinebanking.hackathon.Service.AccountService;
import com.onlinebanking.hackathon.Service.TransactionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequest request) {
        transactionService.transferFunds(request.getFromAccountId(), request.getToAccountId(), request.getAmount(), request.getComment());
        return ResponseEntity.ok().body("Transfer successful");
    }

    @GetMapping("/last10/{accountNumber}")
    public List<Transaction> getLast10Transactions(@PathVariable String accountNumber) {
        return transactionService.findLast10TransactionsByAccountNumber(accountNumber);
    }

    @GetMapping("/transactionDetails/{id}")
    public Transaction transactionDetails(@PathVariable Long id) {
        return transactionService.findById(id);
    }
}

@Data
class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String comment;
}

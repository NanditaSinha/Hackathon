package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.LoginResponse;
import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequest;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/transferfromAccount")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequestByAccountNumber request) {

        accountService.transferFundsfromAccount(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount(), request.getComment());

        String accountUrl = "/hackathon/accounts/findAccountsByFromAccountNumber/{fromAccountNumber}" + request.getFromAccountNumber();
        LoginResponse response = new LoginResponse("Transfer Successful", accountUrl);


        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/transactionDetails/{id}")
    public Transaction transactionDetails(@PathVariable Long id) {
        return transactionService.findById(id);
    }


    @GetMapping("/{accountNumber}/last10")
    public ResponseEntity<List<TransactionDTO>> getLast10TransactionsbyAccountnumber(@PathVariable Long accountNumber) {
        List<TransactionDTO> transactions = transactionService.getLast10Transactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

}





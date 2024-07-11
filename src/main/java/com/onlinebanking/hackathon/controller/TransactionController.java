package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequest;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<?> transferFunds(Principal principal, @RequestBody TransferRequestByAccountNumber request) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToUser(username, request.getFromAccountNumber())) {
            throw new UnauthorizedException("Customer with username " + username + " is not authorized for transfer from Account - " + request.getFromAccountNumber());
        }

        accountService.transferFundsfromAccount(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount(), request.getComment());
        return ResponseEntity.ok().body("Transfer Successful");
    }


    @GetMapping("/transactionDetails/{id}")
    public Transaction transactionDetails(@PathVariable Long id) {
        return transactionService.findById(id);
    }


    @GetMapping("/{accountNumber}/last10")
    public ResponseEntity<List<TransactionDTO>> getLast10TransactionsbyAccountnumber(Principal principal,@PathVariable Long accountNumber) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToUser(username, accountNumber)) {
            throw new UnauthorizedException("Customer with username " + username + " does not have access to requested account number");
        }

        List<TransactionDTO> transactions = transactionService.getLast10Transactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

}





package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.entity.Transaction;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

 /* @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequest request) {
        transactionService.transferFunds(request.getFromAccountId(), request.getToAccountId(), request.getAmount(), request.getComment());

        WebMvcLinkBuilder linkToFromAccount = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).getAccountDetails(request.getFromAccountId()));


        return ResponseEntity.ok(EntityModel.of("Transfer successful")
                .add(linkToFromAccount.withRel("fromAccountDetails")));
    }*/

   /* @GetMapping("/last10/{accountNumber}")
    public List<Transaction> getLast10Transactions(@PathVariable String accountNumber) {
        return transactionService.findLast10TransactionsByAccountNumber(accountNumber);
    }*/

    @GetMapping("/last10/{accountNumber}")
    public CollectionModel<Transaction> getLast10Transactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.findLast10TransactionsByAccountNumber(accountNumber);

        // Add link to account details
        Link accountLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountController.class)
                .getAccountByAccountNumber(accountNumber)).withRel("account-details");

        CollectionModel<Transaction> transactionResources = CollectionModel.of(transactions, accountLink);

        return transactionResources;
    }

    @GetMapping("/transactionDetails/{id}")
    public Transaction transactionDetails(@PathVariable Long id) {
        return transactionService.findById(id);
    }

   /* @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long id) {
        Account account = transactionService.getAccountDetails(id);
        return ResponseEntity.ok(account);
    }*/
}

@Data
class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String comment;
}

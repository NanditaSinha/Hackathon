package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.TransactionDTO;
import com.onlinebanking.hackathon.dto.TransferRequestByAccountNumber;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Operation(summary = "Transfer funds from one account to other account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer funds from one account to other account")
    })
    @PostMapping("/transferfromAccount")
    public ResponseEntity<String> transferFunds(Principal principal, @Valid  @RequestBody TransferRequestByAccountNumber request) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToCustomer(username, request.getFromAccountNumber())) {
            throw new UnauthorizedException("Customer with username " + username + " is not authorized for transfer from Account - " + request.getFromAccountNumber());
        }

        accountService.transferFundsfromAccount(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount(), request.getComment());
        return ResponseEntity.ok().body("Transfer from Account number " + request.getFromAccountNumber() + " to Account number: " + request.getToAccountNumber() + " Successful"  );
    }

    @Operation(summary = "Fetch last 10 transaction for the account number ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch last 10 transaction for the account number ")
    })
    @GetMapping("/{accountNumber}/last10")
    public ResponseEntity<List<TransactionDTO>> getLast10TransactionsbyAccountnumber(Principal principal,@PathVariable Long accountNumber) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToCustomer(username, accountNumber)) {
            throw new UnauthorizedException("Customer with username " + username + " does not have access to requested account number");
        }

        List<TransactionDTO> transactions = transactionService.getLast10Transactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

}





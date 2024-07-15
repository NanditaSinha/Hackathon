package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.AccountDTO;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Fetch Accounts details based on Authenticated Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch all the accounts for the customer")
    })
    @GetMapping("/findAccountsByUsername")
    public List<AccountDTO> findAccountByCustomerId(Principal principal) {
        String username = principal.getName();
        Optional<Customer> customerOpt = customerService.findOptionalByUsername(username);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with id " + username + " not found");
        }

        Customer customer = customerOpt.get();
        List<AccountDTO> accountDtos = accountService.findByCustomer(customer);
        return accountDtos;
    }

    @Operation(summary = "Fetch Account detail for a specific account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch the account detail for the Account Number")
    })
    @GetMapping("/accountdetail/{accountNumber}")
    public AccountDTO getAccountByAccountNumber(@PathVariable Long accountNumber, Principal principal) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToCustomer(username, accountNumber)) {
            throw new UnauthorizedException("Customer with username " + username + " does not have access to requested account number");
        }

        Account account = accountService.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return accountService.getAccountDTO(account);
    }


    @Operation(summary = "Add new customer with encrypted password ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New customer added")
    })
    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = accountService.createCustomer(customer);
        CustomerDTO customerDTO = accountService.getCustomerDTO(createdCustomer);
        return ResponseEntity.ok(customerDTO);
    }

}

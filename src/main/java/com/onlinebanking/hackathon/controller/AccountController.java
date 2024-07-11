package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.dto.AccountDTO;
import com.onlinebanking.hackathon.dto.CustomerDTO;
import com.onlinebanking.hackathon.entity.Account;
import com.onlinebanking.hackathon.entity.Customer;
import com.onlinebanking.hackathon.exception.UnauthorizedException;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.AccountService;
import com.onlinebanking.hackathon.service.CustomerService;
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

    @GetMapping("/findAccountByUsername")
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

    @GetMapping("/accountdetail/{accountNumber}")
    public AccountDTO getAccountByAccountNumber(@PathVariable Long accountNumber, Principal principal) {

        String username = principal.getName();
        if (!accountService.isAccountBelongToUser(username, accountNumber)) {
            throw new UnauthorizedException("Customer with username " + username + " does not have access to requested account number");
        }

        Account account = accountService.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return accountService.getAccountDTO(account);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = accountService.createCustomer(customer);
        CustomerDTO customerDTO = accountService.getCustomerDTO(createdCustomer);
        return ResponseEntity.ok(customerDTO);
    }

}

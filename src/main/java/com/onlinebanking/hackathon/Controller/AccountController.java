package com.onlinebanking.hackathon.Controller;

import com.onlinebanking.hackathon.Entity.Account;
import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Entity.CustomerUser;
import com.onlinebanking.hackathon.Entity.Transaction;
import com.onlinebanking.hackathon.Exception.UserNotFoundException;
import com.onlinebanking.hackathon.Service.AccountService;
import com.onlinebanking.hackathon.Service.CustomerService;
import com.onlinebanking.hackathon.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Account> getAccounts(Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Customer not found"));
        return accountService.findByCustomer(customer);
    }

    @GetMapping("/findAccountByCustomerId/{id}")
    public CollectionModel<EntityModel<Account>> findAccountByCustomerId(@PathVariable long id) {
        Optional<Customer> customerOpt = customerService.findById(id);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with id " + id + " not found");
        }

        Customer customer = customerOpt.get();
        List<Account> accounts = accountService.findByCustomer(customer);

        List<EntityModel<Account>> accountModels = accounts.stream()
                .map(account -> EntityModel.of(account))
                .toList();

        CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(accountModels);


        // Uncomment and adjust the HATEOAS link part if needed
        /*
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCustomers());
        entityModel.add(link.withRel("all-customers"));
        */

        return collectionModel;
    }

    @GetMapping("/findAccountByUsername/{username}")
    public CollectionModel<EntityModel<Account>> findAccountByCustomerId(@PathVariable String username) {
        Optional<Customer> customerOpt = customerService.findByUsername(username);

        if (!customerOpt.isPresent()) {
            throw new UserNotFoundException("Customer with id " + username + " not found");
        }

        Customer customer = customerOpt.get();
        List<Account> accounts = accountService.findByCustomer(customer);

        List<EntityModel<Account>> accountModels = accounts.stream()
                .map(account -> EntityModel.of(account))
                .toList();

        CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(accountModels);


        /*
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCustomers());
        entityModel.add(link.withRel("all-customers"));
        */
        return collectionModel;
    }


/*   @GetMapping("/{id}/transactions")
    public List<Transaction> getRecentTransactions(@PathVariable Long id, @RequestParam(defaultValue = "10") int limit) {
       List<Account> account = accountService.findByCustomerId(id);
       if (account.isEmpty()) {
           throw new UserNotFoundException("No Transaction found for the account");
       }
        return transactionService.findRecentTransactions((Account) account, limit);
    }*/
}

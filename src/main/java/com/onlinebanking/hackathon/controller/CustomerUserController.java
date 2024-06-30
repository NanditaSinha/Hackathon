package com.onlinebanking.hackathon.controller;

import com.onlinebanking.hackathon.entity.CustomerUser;
import com.onlinebanking.hackathon.exception.UserNotFoundException;
import com.onlinebanking.hackathon.service.CustomerUserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerUserController {

    private CustomerUserService customerService;

    public CustomerUserController(CustomerUserService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/findcustomers")
    public List<CustomerUser> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public EntityModel<CustomerUser> findcustomersbyId(@PathVariable long id) {
        CustomerUser customerUser = customerService.getCustomerById(id);

        if(customerUser ==null)
            throw new UserNotFoundException("Customer with id " + id + " not found");

        EntityModel<CustomerUser> entityModel = EntityModel.of(customerUser);

        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllCustomers());
        entityModel.add(link.withRel("all-customers"));

        return entityModel;
    }

    @DeleteMapping("/customer/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }


    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerUser> createUser(@Valid @RequestBody CustomerUser user) {

        CustomerUser savedUser = customerService.saveCustomer(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getCustomer_id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}

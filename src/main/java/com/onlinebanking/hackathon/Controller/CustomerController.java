package com.onlinebanking.hackathon.Controller;

import com.onlinebanking.hackathon.Entity.Customer;
import com.onlinebanking.hackathon.Exception.UserNotFoundException;
import com.onlinebanking.hackathon.Repository.CustomerRepository;
import com.onlinebanking.hackathon.Service.CustomerService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/findcustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public EntityModel<Customer> findcustomersbyId(@PathVariable long id) {
        Customer customer = customerService.getCustomerById(id);

        if(customer==null)
            throw new UserNotFoundException("id:"+id);

        EntityModel<Customer> entityModel = EntityModel.of(customer);

        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllCustomers());
        entityModel.add(link.withRel("all-customers"));

        return entityModel;
    }

    @DeleteMapping("/customer/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        customerService.deleteCustomer(id);
    }


    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> createUser(@Valid @RequestBody Customer user) {

        Customer savedUser = customerService.saveCustomer(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}

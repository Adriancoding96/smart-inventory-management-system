package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CustomerDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDTO));
    }

    @PostMapping("/customers/from-existing-user/{userId}")
    public ResponseEntity<CustomerDTO> createCustomerFromExistingUser(@RequestBody AddressDTO addressDTO, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomerFromExistingUser(addressDTO, userId));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO, id));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}


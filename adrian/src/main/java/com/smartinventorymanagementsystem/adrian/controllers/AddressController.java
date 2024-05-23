package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.saveAddress(addressDTO));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.updateAddress(addressDTO));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}

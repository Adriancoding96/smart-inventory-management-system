package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAllCustomers();
    CustomerDTO findCustomerById(Long id);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO createCustomerFromExistingUser(AddressDTO addressDTO, Long id);
    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);
    void deleteCustomer(long id);
}

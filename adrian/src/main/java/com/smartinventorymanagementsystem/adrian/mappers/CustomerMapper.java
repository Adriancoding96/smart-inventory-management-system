package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CustomerDTO;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import com.smartinventorymanagementsystem.adrian.models.Address;
import com.smartinventorymanagementsystem.adrian.models.Customer;
import com.smartinventorymanagementsystem.adrian.models.User;
import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public CustomerMapper(ModelMapper modelMapper, UserMapper userMapper, AddressMapper addressMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    public CustomerDTO toDTO(Customer customer) {
        try {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            if (customer.getUser() != null) {
                UserDTO userDTO = userMapper.toDTO(customer.getUser());
                customerDTO.setUserDTO(userDTO);
            }
            if (customer.getAddress() != null) {
                AddressDTO addressDTO = addressMapper.toDTO(customer.getAddress());
                customerDTO.setAddressDTO(addressDTO);
            }
            return customerDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map Customer to CustomerDTO", exception);
        }
    }

    public Customer toCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            if (customerDTO.getUserDTO() != null) {
                User user = userMapper.toUser(customerDTO.getUserDTO());
                customer.setUser(user);
            }
            if (customerDTO.getAddressDTO() != null) {
                Address address = addressMapper.toAddress(customerDTO.getAddressDTO());
                customer.setAddress(address);
            }
            return customer;
        } catch (Exception exception) {
            throw new MappingException("Unable to map CustomerDTO to Customer", exception);
        }
    }
}


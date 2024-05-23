package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.dtos.CustomerDTO;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import com.smartinventorymanagementsystem.adrian.mappers.AddressMapper;
import com.smartinventorymanagementsystem.adrian.mappers.CustomerMapper;
import com.smartinventorymanagementsystem.adrian.mappers.UserMapper;
import com.smartinventorymanagementsystem.adrian.models.Address;
import com.smartinventorymanagementsystem.adrian.models.Customer;
import com.smartinventorymanagementsystem.adrian.models.User;
import com.smartinventorymanagementsystem.adrian.repositories.AddressRepository;
import com.smartinventorymanagementsystem.adrian.repositories.CustomerRepository;
import com.smartinventorymanagementsystem.adrian.repositories.UserRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.CustomerService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository,
                               AddressRepository addressRepository, CustomerMapper customerMapper,
                               AddressMapper addressMapper, UserMapper userMapper) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerRepository.findAll();
        logger.debug("Fetched {} customers", customers.size());
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        logger.info("Fetching customer by id: {}", id);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Customer with id: {} not found", id);
            throw new EntityNotFoundException("Customer with id: " + id + " not found");
        });
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Creating new customer: {}", customerDTO);;
        Customer customer = customerMapper.toCustomer(customerDTO);
        customer.setUser(saveUser(customerDTO.getUserDTO()));
        customer.setAddress(saveAddress(customerDTO.getAddressDTO()));
        Customer savedCustomer = customerRepository.save(customer);
        logger.debug("Customer created with id: {}", savedCustomer.getId());
        return customerMapper.toDTO(savedCustomer);
    }

    private User saveUser(UserDTO userDTO) {
        logger.info("Saving user {}", userDTO);
        User user = userMapper.toUser(userDTO);
        return userRepository.save(user);
    }

    private Address saveAddress(AddressDTO addressDTO) {
        logger.info("Saving address {}", addressDTO);
        Address address = addressMapper.toAddress(addressDTO);
        return addressRepository.save(address);
    }

    @Override
    public CustomerDTO createCustomerFromExistingUser(AddressDTO addressDTO, Long userId) {
        logger.info("Creating new customer from existing user id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("User with id: {} not found", userId);
            throw new EntityNotFoundException("User with id: " + userId + " not found");
        });
        Address address = addressMapper.toAddress(addressDTO);
        Address savedAddress = addressRepository.save(address);
        Customer customer = new Customer();
        customer.setUser(user);
        customer.setAddress(savedAddress);
        Customer savedCustomer = customerRepository.save(customer);
        logger.debug("Customer created with id: {}", savedCustomer.getId());
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
        logger.info("Updating customer with id: {}", id);
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Customer with id: {} not found", id);
            throw new EntityNotFoundException("Customer with id: " + id + " not found");
        });
        customer.setAddress(addressMapper.toAddress(customerDTO.getAddressDTO()));
        Customer updatedCustomer = customerRepository.save(customer);
        logger.debug("Customer updated with id: {}", updatedCustomer.getId());
        return customerMapper.toDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(long id) {
        logger.info("Deleting customer with id: {}", id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            logger.debug("Customer with id: {} deleted", id);
        } else {
            logger.warn("Customer with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Customer with id: " + id + " not found");
        }
    }
}


package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.mappers.AddressMapper;
import com.smartinventorymanagementsystem.adrian.models.Address;
import com.smartinventorymanagementsystem.adrian.repositories.AddressRepository;
import com.smartinventorymanagementsystem.adrian.repositories.CustomerRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AddressServiceImpl(AddressMapper addressMapper, AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        logger.info("Fetching all addresses");
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        logger.info("Fetching address by id: {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        return addressMapper.toDTO(address);
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        logger.info("Saving address: {}", addressDTO);
        Address address = addressRepository.save(addressMapper.toAddress(addressDTO));
        return addressMapper.toDTO(address);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO) {
        logger.info("Updating address: {}", addressDTO);
        Address address = addressMapper.toAddress(addressDTO);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDTO(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        logger.info("Deleting address: {}", id);
        addressRepository.deleteById(id);
    }
}


package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;
import com.smartinventorymanagementsystem.adrian.models.Address;
import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public AddressDTO toDTO(Address address) {
        try {
            return modelMapper.map(address, AddressDTO.class);
        } catch (Exception exception) {
            throw new MappingException("Unable to map Address to AddressDTO", exception);
        }
    }

    public Address toAddress(AddressDTO addressDTO) {
        try {
            return modelMapper.map(addressDTO, Address.class);
        } catch (Exception exception) {
            throw new MappingException("Unable to map AddressDTO to Address", exception);
        }
    }
}

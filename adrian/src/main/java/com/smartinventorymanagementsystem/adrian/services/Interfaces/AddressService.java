package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(Long id);
    AddressDTO saveAddress(AddressDTO addressDTO);
    AddressDTO updateAddress(AddressDTO addressDTO);
    void deleteAddress(Long id);

}

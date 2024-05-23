package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private UserDTO userDTO;
    private AddressDTO addressDTO;
}

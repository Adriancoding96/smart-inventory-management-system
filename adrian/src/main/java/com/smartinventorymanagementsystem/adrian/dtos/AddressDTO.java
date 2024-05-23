package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String country;
    private String county;
    private String city;
    private String postCode;
    private String streetName;
    private int streetNumber;
    private int apartmentFloor;
    private int apartmentNumber;
}

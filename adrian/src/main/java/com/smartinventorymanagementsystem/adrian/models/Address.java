package com.smartinventorymanagementsystem.adrian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address extends BaseEntity{

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String county;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private int streetNumber;

    private int apartmentFloor;

    private int apartmentNumber;

    @OneToMany(mappedBy = "address",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Customer> customers;
}

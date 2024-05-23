package com.smartinventorymanagementsystem.adrian.repositories;

import com.smartinventorymanagementsystem.adrian.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

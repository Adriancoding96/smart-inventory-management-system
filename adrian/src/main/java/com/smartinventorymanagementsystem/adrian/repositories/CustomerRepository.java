package com.smartinventorymanagementsystem.adrian.repositories;

import com.smartinventorymanagementsystem.adrian.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

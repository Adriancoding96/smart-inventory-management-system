package com.smartinventorymanagementsystem.adrian.repositories;

import com.smartinventorymanagementsystem.adrian.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);
}

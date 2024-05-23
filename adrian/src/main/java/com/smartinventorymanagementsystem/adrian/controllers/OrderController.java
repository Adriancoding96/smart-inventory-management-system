package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.OrderAndOrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/status")
    public ResponseEntity<List<OrderAndOrderStatusDTO>> getAllOrdersWithStatus() {
        return ResponseEntity.ok(orderService.getAllOrdersWithStatus());
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomer(customerId));
    }

    @GetMapping("/orders/customer/{customerId}/status")
    public ResponseEntity<List<OrderAndOrderStatusDTO>> getAllOrdersWithStatusByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersWithStatusByCustomer(customerId));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/orders/{id}/status")
    public ResponseEntity<OrderAndOrderStatusDTO> getOrderWithStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderWithStatusById(id));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderAndOrderStatusDTO> newOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.newOrder(orderDTO));
    }

    @PutMapping("/orders")
    public ResponseEntity<OrderAndOrderStatusDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderDTO));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

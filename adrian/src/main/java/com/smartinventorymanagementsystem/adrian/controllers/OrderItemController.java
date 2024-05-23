package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.OrderItemDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/order_item")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems(){
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/order_item/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @PostMapping("/order_item")
    public ResponseEntity<OrderItemDTO> saveOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.saveOrderItem(orderItemDTO));
    }

    @PutMapping("/order_item")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@RequestBody OrderItemDTO orderItemDTO){
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemDTO));
    }

    @DeleteMapping("/order_item/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}

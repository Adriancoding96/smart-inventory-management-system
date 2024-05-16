package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDTO> getAllOrderItems();
    OrderItemDTO getOrderItemById(Long id);
    OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO);
    void deleteOrderItem(Long id);
}

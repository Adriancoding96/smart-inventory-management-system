package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.OrderAndOrderStatusDTO;
import com.smartinventorymanagementsystem.adrian.dtos.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();
    List<OrderAndOrderStatusDTO> getAllOrdersWithStatus();
    List<OrderDTO> getAllOrdersByCustomer(Long customerId);
    List<OrderAndOrderStatusDTO> getAllOrdersWithStatusByCustomer(Long customerId);
    OrderDTO getOrderById(Long id);
    OrderAndOrderStatusDTO getOrderWithStatusById(Long id);
    OrderAndOrderStatusDTO newOrder(OrderDTO orderDTO);
    OrderAndOrderStatusDTO updateOrder(OrderDTO orderDTO);
    void deleteOrder(Long id);


}

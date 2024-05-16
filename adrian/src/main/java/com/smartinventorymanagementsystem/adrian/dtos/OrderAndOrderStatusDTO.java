package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderAndOrderStatusDTO {

    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private OrderStatusDTO orderStatusDTO;

}

package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private long id;
    private long productId;
    private int quantity;
    private BigDecimal totalPrice;
}

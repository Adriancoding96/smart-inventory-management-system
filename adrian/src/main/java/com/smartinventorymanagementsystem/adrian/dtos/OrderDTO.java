package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrderDTO {

    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;

}

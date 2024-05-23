package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusDTO {
    private Long id;
    private Long orderId;
    private String status;
    private LocalDateTime expectedShippingDate;
    private LocalDateTime expectedDeliveryDate;
    private String deliveryMethod;
    private String deliveryCompany;
    private boolean delivered;
}


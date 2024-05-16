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

    /*
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime expectedShippingDate;

    @Column(nullable = false)
    private LocalDateTime expectedDeliveryDate;

    @Column(nullable = false)
    private String deliveryMethod;

    @Column(nullable = false)
    private String deliveryCompany;

    * */
}

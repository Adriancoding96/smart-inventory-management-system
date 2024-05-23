package com.smartinventorymanagementsystem.adrian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private LocalDateTime orderDate;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private String requestedOrderMethod;
    @ManyToOne
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

}

package com.smartinventorymanagementsystem.adrian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery_methods")
public class DeliveryMethod extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "delivery_company_id", nullable = false)
    private DeliveryCompany deliveryCompany;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private int estimatedDeliveryDays;
}


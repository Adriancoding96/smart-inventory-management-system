package com.smartinventorymanagementsystem.adrian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery_companies")
public class DeliveryCompany extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "deliveryCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeliveryMethod> deliveryMethods;
}


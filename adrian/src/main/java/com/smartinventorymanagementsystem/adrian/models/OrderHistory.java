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
@Table(name = "order_histories")
public class OrderHistory extends BaseEntity {

    private Long customerId;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> pastOrders;

}

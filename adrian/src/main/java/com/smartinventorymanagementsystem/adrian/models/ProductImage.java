package com.smartinventorymanagementsystem.adrian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_images")
public class ProductImage extends BaseEntity{
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY) //Ensures image does not get fetched unless specified
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

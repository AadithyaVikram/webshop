package com.epam.training.order.data.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    private Long id;
    private String description;
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Long packageId;
}

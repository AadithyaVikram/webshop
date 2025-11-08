package com.epam.training.shipping.data.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "PACKAGES")
public class Package {
    @Id
    private Long id;
    private String status;
    private LocalDate expectedShippingDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

package com.epam.training.order.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    private long id;
    private String email;
}

package com.epam.training.shipping.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    private long id;
    private String email;
}

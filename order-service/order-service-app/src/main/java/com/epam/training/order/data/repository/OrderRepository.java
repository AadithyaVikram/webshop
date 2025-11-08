package com.epam.training.order.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.training.order.data.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Email(String email);
}

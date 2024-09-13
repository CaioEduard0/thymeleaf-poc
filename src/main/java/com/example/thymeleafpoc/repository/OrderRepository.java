package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

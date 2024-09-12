package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

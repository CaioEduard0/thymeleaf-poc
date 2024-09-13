package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContaining(Pageable pageable, String name);

    @Query(nativeQuery = true, value = """
        select * from product
        where active is true
        and name like CONCAT('%', :search, '%')
        or description like CONCAT('%', :search, '%')
        or category like CONCAT('%', :search, '%')
    """)
    Page<Product> findBySearch(String search, Pageable pageable);
}

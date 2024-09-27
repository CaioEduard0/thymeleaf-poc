package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.enums.Category;
import com.example.thymeleafpoc.model.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = """
        select id, name, category, description, price, created_at, updated_at, active
        from product
        where active is true
        and (name like CONCAT('%', :search, '%')
        or category like CONCAT('%', :search, '%')
        or description like CONCAT('%', :search, '%')
        or price like CONCAT('%', :search, '%'))
    """)
    Page<Product> findAll(String search, Pageable pageable);

    @Query("""
        select p from Product p where p.active = true
    """)
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    @Query(value = """
        update Product p set p.active = false where p.id = ?1
    """)
    @Modifying
    void delete(@NonNull Long id);

    @Query(value = """
        select p.price from Product p where p.active = true
    """)
    List<BigDecimal> getPrices();

    @Query(value = """
        select p.category from Product p where p.active = true
    """)
    List<Category> getCategories();
}

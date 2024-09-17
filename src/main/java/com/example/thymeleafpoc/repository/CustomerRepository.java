package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.model.Customer;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(nativeQuery = true, value = """
        select id, name, email, birth_date, active
        from customer
        where active is true
        and (name like CONCAT('%', :search, '%')
        or email like CONCAT('%', :search, '%')
        or birth_date like CONCAT('%', :search, '%'))
    """)
    Page<Customer> findAllBySearch(String search, Pageable pageable);

    @Query("""
        select c from Customer c
        where c.active=true
    """)
    @NonNull
    Page<Customer> findAll(@NonNull Pageable pageable);

    @Query(value = """
        update Customer c set c.active=false where c.id=?1
    """)
    @Modifying
    void deleteById(@NonNull Long id);
}

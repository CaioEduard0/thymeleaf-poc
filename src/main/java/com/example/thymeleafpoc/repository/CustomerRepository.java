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
        select * from customer
        where active is true
        and name like '%?1%'
        or email like '%?1%'
        or birth_date like '%?1%'
    """)
    Page<Customer> findAllBySearch(String search, Pageable pageable);

    @Query(nativeQuery = true, value = """
        update customer set active=false where id='?1'
    """)
    @Modifying
    void deleteById(@NonNull Long id);
}

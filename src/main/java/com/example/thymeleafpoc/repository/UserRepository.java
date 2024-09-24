package com.example.thymeleafpoc.repository;

import com.example.thymeleafpoc.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = """
        select id, email, password, authorities, created_at, updated_at, active
        from user
        where email like CONCAT('%', :search, '%')
        or authorities like CONCAT('%', :search, '%')
    """)
    Page<User> findAll(String search, Pageable pageable);

    @Query(nativeQuery = true, value = """
        select id, email, password, authorities, created_at, updated_at, active
        from user
    """)
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Query(value = """
        update User u set u.authorities = 'ADMIN' where u.id = ?1
    """)
    @Modifying
    void makeAdmin(@NonNull Long id);

    @Query(value = """
        update User u set u.active = false where u.id = ?1
    """)
    @Modifying
    void delete(@NonNull Long id);

    @Query(value = """
        update User u set u.active = true where u.id = ?1
    """)
    @Modifying
    void activate(@NonNull Long id);
}

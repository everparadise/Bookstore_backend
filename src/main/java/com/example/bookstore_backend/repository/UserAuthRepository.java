package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    @Query("SELECT COUNT(u) FROM UserAuth u WHERE u.username = :username AND u.password = :password")
    int existsByUsernameAndPassword(String username, String password);
}

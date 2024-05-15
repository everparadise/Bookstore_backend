package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.model.CartBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;


public interface CartbookRepository extends JpaRepository<CartBook, Long> {
    List<CartBook> getCartbookByUid(Long uid);

    @Modifying
    @Transactional
    void deleteCartBookByCid(Long cid);
}

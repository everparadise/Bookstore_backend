package com.example.main.repository;

import com.example.main.model.CartBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartbookRepository extends JpaRepository<CartBook, Long> {
    List<CartBook> findByUser_Uid(Long uid);

    @Modifying
    @Transactional
    void deleteCartBookByCid(Long cid);
}

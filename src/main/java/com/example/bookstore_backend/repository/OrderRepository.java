package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderBook, Long> {
    public List<OrderBook> getOrderBookByUid(Long uid);
}

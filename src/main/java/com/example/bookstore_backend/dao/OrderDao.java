package com.example.bookstore_backend.dao;

import com.example.bookstore_backend.dto.BookSalesDto;
import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.UserConsumingDto;
import com.example.bookstore_backend.model.Order;
import com.example.bookstore_backend.model.OrderBook;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    public List<OrderBookDto> getOrderBooksByUid(Long uid);
    public Page<OrderBookDto> getOrders(String value, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Long uid);
    public Order save(Order order);
    public Page<BookSalesDto> getBookByRangingAndUid(LocalDateTime startDate, LocalDateTime endDate, Integer limit, Long uid);
    public Page<UserConsumingDto> getUserByRangingAndConsuming(LocalDateTime startDate, LocalDateTime endDate, Integer limit);
}

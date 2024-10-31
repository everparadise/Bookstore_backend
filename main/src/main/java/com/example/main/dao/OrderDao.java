package com.example.main.dao;

import com.example.main.dto.BookSalesDto;
import com.example.main.dto.OrderBookDto;
import com.example.main.dto.UserConsumingDto;
import com.example.main.model.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {
    public List<OrderBookDto> getOrderBooksByUid(Long uid);
    public Page<OrderBookDto> getOrders(String value, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Long uid);
    public Order save(Order order);
    public Page<BookSalesDto> getBookByRangingAndUid(LocalDateTime startDate, LocalDateTime endDate, Integer limit, Long uid);
    public Page<UserConsumingDto> getUserByRangingAndConsuming(LocalDateTime startDate, LocalDateTime endDate, Integer limit);
}

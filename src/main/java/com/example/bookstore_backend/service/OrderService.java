package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.OrderDto;
import com.example.bookstore_backend.dto.OrderRequestDto;
import com.example.bookstore_backend.model.Order;
import com.example.bookstore_backend.model.OrderBook;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    public Order AddOrder(OrderDto order) throws Exception;
    public List<OrderBookDto> GetOrderByUid(Long uid);
    public Page<OrderBookDto> getOrderByFilter(OrderRequestDto filter, Long uid);
}

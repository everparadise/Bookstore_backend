package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.OrderBook;

import java.util.List;

public interface OrderService {
    public OrderBook AddOrderItemInfo(OrderBook order);
    public List<OrderBookDto> GetOrderByUid(Long uid);
}

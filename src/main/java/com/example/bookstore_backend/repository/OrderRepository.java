package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.OrderBook;

import java.util.List;

public interface OrderRepository {
    public Boolean AddOrderItemInfo(OrderBook order);
    public List<OrderBookDto> GetOrderByUid(Integer uid);
}

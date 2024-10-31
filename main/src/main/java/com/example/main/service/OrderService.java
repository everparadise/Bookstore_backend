package com.example.main.service;

import com.example.main.dto.OrderBookDto;
import com.example.main.dto.OrderDto;
import com.example.main.dto.OrderRequestDto;
import com.example.main.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    public Order AddOrder(OrderDto order) throws Exception;
    public List<OrderBookDto> GetOrderByUid(Long uid);
    public Page<OrderBookDto> getOrderByFilter(OrderRequestDto filter, Long uid);
}

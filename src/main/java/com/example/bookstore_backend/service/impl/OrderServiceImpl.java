package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.repository.impl.OrderRepositoryImpl;
import com.example.bookstore_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepositoryImpl orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderBookDto> GetOrderByUid(Integer uid) {
        return orderRepository.GetOrderByUid(uid);
    }

    @Override
    public Boolean AddOrderItemInfo(OrderBook order) {
        order.setDateTime(LocalDateTime.now());
        return orderRepository.AddOrderItemInfo(order);
    }
}

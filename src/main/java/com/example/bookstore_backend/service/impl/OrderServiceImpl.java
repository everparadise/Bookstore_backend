package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.BookRepository;
import com.example.bookstore_backend.repository.OrderRepository;
import com.example.bookstore_backend.repository.UserRepository;
import com.example.bookstore_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    BookRepository bookRepository;
    UserRepository userRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BookRepository bookRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderBookDto> GetOrderByUid(Long uid) {
        List<OrderBook> orders = orderRepository.getOrderBookByUid(uid);
        return orders.stream().map(this::mapToOrderDto).toList();
    }

    @Override
    public OrderBook AddOrderItemInfo(OrderBook order) {
        order.setDateTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public OrderBookDto mapToOrderDto(OrderBook orderBook){
        Book book = bookRepository.getBookByBid(orderBook.getBid()).get();
        User user = userRepository.getUserByUid(orderBook.getUid()).get();
        return OrderBookDto.builder()
                .pic(book.getPic())
                .name(book.getName())
                .address(orderBook.getAddress())
                .number(orderBook.getNumber())
                .telephone(orderBook.getTelephone())
                .username(orderBook.getName())
                .dateTime(orderBook.getDateTime())
                .build();
    }
}

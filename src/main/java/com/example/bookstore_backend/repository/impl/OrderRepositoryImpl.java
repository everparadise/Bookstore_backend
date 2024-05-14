package com.example.bookstore_backend.repository.impl;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean AddOrderItemInfo(OrderBook order) {
        String url = "INSERT INTO orderbooks ( uid, bid, number, dateTime, totalPrice, address, telephone) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(url, order.getUid(),order.getBid(),order.getNumber(), order.getDateTime(), order.getTotalPrice(), order.getAddress(), order.getTelephone());
        return true;
    }

    @Override
    public List<OrderBookDto> GetOrderByUid(Integer uid) {
        String url = "SELECT * FROM orderbooks INNER JOIN books ON books.bid = orderbooks.bid INNER JOIN users ON orderbooks.uid = users.uid WHERE orderbooks.uid = ? ";

        return jdbcTemplate.query(url, new BeanPropertyRowMapper<>(OrderBookDto.class), uid);
    }
}

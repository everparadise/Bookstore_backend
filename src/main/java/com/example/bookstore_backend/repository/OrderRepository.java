package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.UserConsumingDto;
import com.example.bookstore_backend.model.Order;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByUser_Uid(Long uid);

    @Query("SELECT new com.example.bookstore_backend.dto.UserConsumingDto(order.user.uid, order.user.username, SUM(order.totalPrice))" +
            "FROM Order order WHERE order.date BETWEEN :startDate AND :endDate " +
            "GROUP BY order.user.uid ORDER BY SUM(order.totalPrice) DESC")
    Page<UserConsumingDto> getUserByRangingAndPaging(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    //public Page<Order> findOrdersByItems_book_nameAndDateAfterAndDateBefore(String value, LocalDateTime beginTime, LocalDateTime endTime, Pageable pageable);
}

package com.example.main.repository;

import com.example.main.dto.UserConsumingDto;
import com.example.main.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByUser_Uid(Long uid);

    @Query("SELECT new com.example.main.dto.UserConsumingDto(order.user.uid, order.user.username, SUM(order.totalPrice))" +
            "FROM Order order WHERE order.date BETWEEN :startDate AND :endDate " +
            "GROUP BY order.user.uid ORDER BY SUM(order.totalPrice) DESC")
    Page<UserConsumingDto> getUserByRangingAndPaging(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    //public Page<Order> findOrdersByItems_book_nameAndDateAfterAndDateBefore(String value, LocalDateTime beginTime, LocalDateTime endTime, Pageable pageable);
}

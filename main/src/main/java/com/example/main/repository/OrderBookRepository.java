package com.example.main.repository;

import com.example.main.dto.BookSalesDto;
import com.example.main.dto.OrderBookDto;
import com.example.main.model.Book;
import com.example.main.model.OrderBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    @Query("SELECT new com.example.main.dto.OrderBookDto(orderbooks.order.address, orderbooks.number, orderbooks.order.date, " +
            "orderbooks.order.user.username, orderbooks.book.pic,orderbooks.book.name, orderbooks.order.phone) " +
            "FROM OrderBook orderbooks WHERE orderbooks.book.name LIKE :name AND orderbooks.order.date BETWEEN :beginTime AND :endTime" +
            " AND (:uid = -1 OR orderbooks.order.user.uid = : uid)")
    Page<OrderBookDto> findOrderBooksByBook_NameAndOrder_DateRange(String name, LocalDateTime beginTime, LocalDateTime endTime, Pageable pageable, Long uid);

    @Query("SELECT new com.example.main.dto.BookSalesDto(orderBook.book.bid, orderBook.book.name, orderBook.book.pic,orderBook.book.price,SUM(orderBook.number))" +
            "FROM OrderBook orderBook WHERE orderBook.order.date BETWEEN :startDate AND :endDate " +
            "AND (:uid = -1 OR orderBook.order.user.uid = :uid)" +
            "GROUP BY orderBook.book.bid " +
            "ORDER BY SUM(orderBook.number) DESC")
    Page<BookSalesDto> getRankingByRangingAndUidAndLimit(LocalDateTime startDate, LocalDateTime endDate, Long uid, Pageable pageable);


    Integer deleteOrderBookByBook(Book book);
}

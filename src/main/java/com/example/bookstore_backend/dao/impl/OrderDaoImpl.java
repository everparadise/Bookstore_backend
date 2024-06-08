package com.example.bookstore_backend.dao.impl;


import com.example.bookstore_backend.Constants.DELETEDORDER;
import com.example.bookstore_backend.dao.OrderDao;
import com.example.bookstore_backend.dto.BookSalesDto;
import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.UserConsumingDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.model.Order;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.repository.OrderBookRepository;
import com.example.bookstore_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bookstore_backend.Constants.DELETEDORDER.NullName;
import static com.example.bookstore_backend.Constants.DELETEDORDER.NullPic;

@Component
public class OrderDaoImpl implements OrderDao {
    OrderRepository orderRepository;
    OrderBookRepository orderBookRepository;
    @Autowired
    OrderDaoImpl(OrderRepository orderRepository, OrderBookRepository orderBookRepository){
        this.orderRepository = orderRepository;
        this.orderBookRepository = orderBookRepository;
    }
    @Override
    public List<OrderBookDto> getOrderBooksByUid(Long uid) {


        List<Order> list = orderRepository.findByUser_Uid(uid);
        List<OrderBookDto> dtoList = new ArrayList<>();
        for(Order order: list){
            for(OrderBook orderBook: order.getItems()){
                Book item = orderBook.getBook();
                boolean flag = item != null;
                OrderBookDto dto =
                        OrderBookDto.builder()
                        .pic(flag ? item.getPic() : NullPic)
                        .name(flag ? item.getName(): NullName)
                        .dateTime(order.getDate())
                        .address(order.getAddress())
                        .number(orderBook.getNumber())
                        .telephone(order.getPhone())
                        .username(order.getReceiver())
                        .build();

                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public Page<OrderBookDto> getOrders(String value, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Long uid) {
        Pageable pageable = PageRequest.of(page, 12,  Sort.by(Sort.Direction.DESC, "order.date"));
        value = "%" + value + "%";
        return orderBookRepository.findOrderBooksByBook_NameAndOrder_DateRange(value, beginTime, endTime, pageable, uid);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<BookSalesDto> getBookByRangingAndUid(LocalDateTime startDate, LocalDateTime endDate, Integer limit, Long uid) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderBookRepository.getRankingByRangingAndUidAndLimit(startDate, endDate, uid, pageable);
    }

    @Override
    public Page<UserConsumingDto> getUserByRangingAndConsuming(LocalDateTime startDate, LocalDateTime endDate, Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderRepository.getUserByRangingAndPaging(startDate, endDate, pageable);
    }
}

package com.example.main.service.impl;

import com.example.main.dao.BookDao;
import com.example.main.dao.CartDao;
import com.example.main.dao.OrderDao;
import com.example.main.dao.UserDao;
import com.example.main.dto.*;
import com.example.main.dto.NewOrderItemDto;
import com.example.main.dto.OrderBookDto;
import com.example.main.dto.OrderDto;
import com.example.main.dto.OrderRequestDto;
import com.example.main.model.Book;
import com.example.main.model.Order;
import com.example.main.model.OrderBook;
import com.example.main.model.User;
import com.example.main.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;
    BookDao bookDao;
    UserDao userDao;
    CartDao cartDao;
    @Autowired
    public OrderServiceImpl(OrderDao orderDao, BookDao bookDao, UserDao userDao, CartDao cartDao){
        this.orderDao = orderDao;
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }


    @Override
    public List<OrderBookDto> GetOrderByUid(Long uid) {
        return orderDao.getOrderBooksByUid(uid);
    }


    @Override
    public Page<OrderBookDto> getOrderByFilter(OrderRequestDto filter, Long uid) {
        LocalDateTime beginTime, endTime;
        if(filter.startTime == null || filter.startTime.isEmpty()){
            beginTime = LocalDateTime.of(1973, 1, 1, 0, 0, 0, 0);
        }
        else beginTime = parseDateTimeString(filter.startTime);
        if(filter.endTime == null || filter.endTime.isEmpty()){
            endTime = LocalDateTime.now();
        }
        else endTime = parseDateTimeString(filter.endTime);
        return orderDao.getOrders(filter.name, beginTime, endTime, filter.page, uid);
    }

    @Transactional
    @Override
    public Order AddOrder(OrderDto dto) throws Exception {
        User user = userDao.getUserByUid(dto.getUid()).get();

        //Order 总信息
        Order order = Order.builder()
                .address(dto.getAddress())
                .date(LocalDateTime.now())
                .phone(dto.getTelephone())
                .receiver(dto.getReceiver())
                .user(user)
                .build();


        order.setUser(user);
        List<OrderBook> orderBookList = new ArrayList<>();
        Integer totalNumbers = 0;
        //Order Item信息
        for(NewOrderItemDto it: dto.getItems()){
            Book book = bookDao.getBookByBid(it.getBid()).orElseThrow(()->new NoSuchElementException("No Target Book" ));
            OrderBook orderBook =OrderBook.builder()
                    .book(book)
                    .number(it.getNumbers())
                    .build();
            orderBook.setOrder(order);
            orderBookList.add(orderBook);
            totalNumbers += it.getNumbers() * book.getPrice();

            //删除对应的CartItem并更新Book sales属性
            bookDao.increaseBookSales(it.getBid(), it.getNumbers());
            bookDao.modifyBookStock(it.getBid(), -it.getNumbers());
            cartDao.deleteCartBookByCid(it.getCid());
        }
        order.setItems(orderBookList);
        order.setTotalPrice(totalNumbers);
        if(user.getRemainMoney() < totalNumbers) throw new RuntimeException("余额不足");
        userDao.decreaseMoney(user.getUid(), totalNumbers);
        orderDao.save(order);
        return order;
    }

    private LocalDateTime parseDateTimeString(String dateTimeString){
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
    }
}

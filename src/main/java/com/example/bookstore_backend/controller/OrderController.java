package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.Constants.Role;
import com.example.bookstore_backend.dto.*;
import com.example.bookstore_backend.model.Order;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.service.CartService;
import com.example.bookstore_backend.service.OrderService;
import com.example.bookstore_backend.service.UserService;
import com.example.bookstore_backend.service.impl.BookServiceImpl;
import com.example.bookstore_backend.service.impl.CartServiceImpl;
import com.example.bookstore_backend.service.impl.OrderServiceImpl;
import com.example.bookstore_backend.util.UserProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.BorderUIResource;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    OrderService orderService;
    BookService bookService;
    CartService cartService;
    UserService userService;
    @Autowired
    public OrderController(OrderService orderService, BookService bookService, CartService cartService, UserService userService){
        this.orderService = orderService;
        this.bookService = bookService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @Transactional
    @PostMapping
    public ResponseDto<Boolean> addOrderItemInfo(@RequestBody OrderDto dto){
        Long uid = UserProvider.getUserUid();
        dto.setUid(uid);
        try{
            orderService.AddOrder(dto);

        }catch(NoSuchElementException e){
            return new ResponseDto<>(false, e.getLocalizedMessage(), false);
        }

        return new ResponseDto<>(true, "operation OK", true);
    }

    @GetMapping
    public ResponseDto<List<OrderBookDto>> getOrderByUid(){
        Long uid = UserProvider.getUserUid();
        List<OrderBookDto> list = orderService.GetOrderByUid(uid);
        return new ResponseDto<>(true, "Operation OK", list);
    }

    @PostMapping("orders")
    public ResponseDto<SimplifiedPageDto<OrderBookDto>> getOrders(@RequestBody OrderRequestDto orderFilter){
        Page<OrderBookDto> orders;
        if(UserProvider.getUserRole() == Role.ADMIN){
            orders = orderService.getOrderByFilter(orderFilter, -1L);
        }
        else{
            Long uid = UserProvider.getUserUid();
            orders = orderService.getOrderByFilter(orderFilter, uid);
        }
        return new ResponseDto<>(true, "Operation OK", new SimplifiedPageDto<>(
                orders.getContent(),
                orders.getTotalPages(),
                (int) orders.getTotalElements()));
    }
}

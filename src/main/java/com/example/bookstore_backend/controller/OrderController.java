package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.service.CartService;
import com.example.bookstore_backend.service.OrderService;
import com.example.bookstore_backend.service.UserService;
import com.example.bookstore_backend.service.impl.BookServiceImpl;
import com.example.bookstore_backend.service.impl.CartServiceImpl;
import com.example.bookstore_backend.service.impl.OrderServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    OrderService orderServiceImpl;
    BookService bookService;
    CartService cartService;
    UserService userService;
    @Autowired
    public OrderController(OrderService orderService, BookService bookService, CartService cartService, UserService userService){
        this.orderServiceImpl = orderService;
        this.bookService = bookService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @Transactional
    @PostMapping("/addOrder/{cid}")
    public ResponseDto<Boolean> addOrderItemInfo(@RequestBody OrderBook book, @PathVariable Long cid){
            if(userService.decreaseMoney(book.getUid(), book.getTotalPrice())){
                book.setDateTime(LocalDateTime.now());
                orderServiceImpl.AddOrderItemInfo(book);
                if(cid != 0) cartService.deleteCartItem(cid);
                bookService.increaseBookSales(book.getBid(), book.getNumber());
                return new ResponseDto<>(true, "Operation OK", true);
            }
            return new ResponseDto<>(true, "not enough money", false);
    }

    @GetMapping("/getOrder/{uid}")
    public ResponseDto<List<OrderBookDto>> getOrderByUid(@PathVariable Long uid){
        List<OrderBookDto> list = orderServiceImpl.GetOrderByUid(uid);
        return new ResponseDto<>(true, "Operation OK", list);
    }
}

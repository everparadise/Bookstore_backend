package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.dto.OrderBookDto;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.model.OrderBook;
import com.example.bookstore_backend.service.impl.BookServiceImpl;
import com.example.bookstore_backend.service.impl.CartServiceImpl;
import com.example.bookstore_backend.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    OrderServiceImpl orderServiceImpl;
    BookServiceImpl bookService;
    CartServiceImpl cartService;
    @Autowired
    public OrderController(OrderServiceImpl orderService, BookServiceImpl bookService, CartServiceImpl cartService){
        this.orderServiceImpl = orderService;
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @PostMapping("/addOrder/{cid}")
    public ResponseDto<Boolean> addOrderItemInfo(@RequestBody OrderBook book, @PathVariable Integer cid){

            book.setDateTime(LocalDateTime.now());
            orderServiceImpl.AddOrderItemInfo(book);
            cartService.deleteCartItem(cid);
            bookService.increaseBookSales(book.getBid(), book.getNumber());

        return new ResponseDto<>(true, "Operation OK", true);
    }

    @GetMapping("/getOrder/{uid}")
    public ResponseDto<List<OrderBookDto>> getOrderByUid(@PathVariable Integer uid){
        List<OrderBookDto> list = orderServiceImpl.GetOrderByUid(uid);
        return new ResponseDto<>(true, "Operation OK", list);
    }
}

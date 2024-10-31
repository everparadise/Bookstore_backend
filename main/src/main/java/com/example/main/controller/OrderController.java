package com.example.main.controller;

import com.example.main.Constants.Role;
import com.example.main.dto.*;
import com.example.main.service.BookService;
import com.example.main.service.CartService;
import com.example.main.service.OrderService;
import com.example.main.service.UserService;
import com.example.main.util.KafkaProducer;
import com.example.main.util.UserProvider;
import com.example.main.dto.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    BookService bookService;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping
    public ResponseDto<Boolean> addOrderItemInfo(@RequestBody OrderDto dto){
        Long uid = UserProvider.getUserUid();
        String token = UserProvider.getToken();
        dto.setToken(token);
        dto.setUid(uid);
        try{
            System.out.println("Send an Order:" + dto);
            kafkaProducer.send("orderTopic", null, dto);
            //orderService.AddOrder(dto);
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

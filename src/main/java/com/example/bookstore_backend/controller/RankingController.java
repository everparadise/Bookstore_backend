package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.Configuration.RankingConfig;
import com.example.bookstore_backend.Constants.Role;
import com.example.bookstore_backend.dto.*;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.service.UserService;
import com.example.bookstore_backend.util.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1/ranking")
public class RankingController {
    RankingConfig rankingConfig;
    UserService userService;
    BookService bookService;

    @Autowired
    RankingController(RankingConfig rankingConfig, UserService userService, BookService bookService){
        this.rankingConfig = rankingConfig;
        this.userService = userService;
        this.bookService = bookService;
    }

    @PostMapping
    public void updateConfig(@RequestBody RankingConfigDto dto){
        rankingConfig.setUserStartTime(parseDateTime(dto.getUserStartTime()));
        rankingConfig.setUserEndTime(parseDateTime(dto.getUserEndTime()));
        rankingConfig.setBookStartTime(parseDateTime(dto.getBookStartTime()));
        rankingConfig.setBookEndTime(parseDateTime(dto.getBookEndTime()));
    }

    @PostMapping("book")
    public ResponseDto<List<BookSalesDto>> getRankingBook(@RequestBody RankingRequestDto request){
        Long uid = UserProvider.getUserRole() == Role.ADMIN ? -1 : UserProvider.getUserUid();
        Integer limit = UserProvider.getUserRole() == Role.ADMIN ? request.getNumber() : Integer.MAX_VALUE;
        List<BookSalesDto> list = bookService.getRanking(request.getStartDateTime(), request.getEndDateTime(), limit, uid);
        return new ResponseDto<>(true, "Operation OK", list);
    }

    @PostMapping("user")
    public ResponseDto<List<UserConsumingDto>> getRankingUser(@RequestBody RankingRequestDto request){
        List<UserConsumingDto> list = userService.getUserByRangingAndLimit(request.getStartDateTime(), request.getEndDateTime(), request.getNumber());
        return new ResponseDto<>(true, "Operation OK", list);
    }


    private LocalDateTime parseDateTime(String input){
        if(input == null || input.isEmpty()){
            return LocalDateTime.of(1973, 1, 1, 0, 0, 0);
        }
        else return LocalDateTime.parse(input);

    }
}

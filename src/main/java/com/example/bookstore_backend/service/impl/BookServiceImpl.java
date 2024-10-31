package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dao.BookDao;
import com.example.bookstore_backend.dao.OrderDao;
import com.example.bookstore_backend.dto.*;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.dao.OrderDao;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.util.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    BookDao bookDao;
    OrderDao orderDao;

    CacheClient cacheClient;
    @Autowired
    public BookServiceImpl(BookDao bookDao, OrderDao orderDao){
        this.bookDao = bookDao;
        this.orderDao = orderDao;
    }

    @Override
    public Page<BookDto> getBooks(Integer page, String value) {
        if(value == null) value = "";
        return bookDao.getBooks(page, value);
    }

    @Override
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value) {
        if(value == null) value = "";
        return bookDao.getExtendBooks(page, value);

    }

    @Override
    public Integer getBooksPages() {
        return bookDao.getBooksPage();
    }

    @Override
    public Optional<Book> getBookByBid(Long bid) {
        return bookDao.getBookByBid(bid);
    }

    @Override
    public void addBookInfo(Book book){
        bookDao.save(book);
    }


    @Override
    public Integer deleteBookByBid(Long bid) {
        return bookDao.deleteBookByBid(bid);
    }

    @Override
    public Boolean modifyBookPic(String pic, Long bid) {
        return bookDao.modifyBookPic(pic, bid) != 0;
    }

    @Override
    public Integer modifyBookInfo(ExtendBookDto dto) {
        return bookDao.modifyBookInfo(dto.getBid(), dto.getName(), dto.getAuthor(), dto.getPic(), dto.getIsbn(), dto.getStock(), dto.getComment());
    }

    @Override
    public List<BookSalesDto> getRanking(String startString, String endString, Integer limit, Long uid) {
        LocalDateTime startTime, endTime;
        if(startString == null || startString.isEmpty()){
            startTime = LocalDateTime.of(1973, 1, 1, 0, 0, 0);
        }
        else startTime = LocalDateTime.parse(startString,DateTimeFormatter.ISO_DATE_TIME);

        if(startString == null || startString.isEmpty()){
            endTime = LocalDateTime.now();
        }
        else endTime = LocalDateTime.parse(startString, DateTimeFormatter.ISO_DATE_TIME);

        Page<BookSalesDto> salesDto = orderDao.getBookByRangingAndUid(startTime, endTime, limit, uid);
        return salesDto.getContent();
    }

    private static BookDto mapToBookDto(ExtendBookDto extendBook){
        return BookDto.builder()
                .bid(extendBook.getBid())
                .pic(extendBook.getPic())
                .price(extendBook.getPrice())
                .name(extendBook.getName())
                .sales(extendBook.getSales())
                .build();

    }
}

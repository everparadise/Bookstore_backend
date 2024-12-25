package com.example.main.service.impl;

import com.example.main.dao.BookDao;
import com.example.main.dao.OrderDao;
import com.example.main.dto.*;
import com.example.main.dto.BookDto;
import com.example.main.dto.BookSalesDto;
import com.example.main.dto.ExtendBookDto;
import com.example.main.model.Book;
import com.example.main.model.Tag;
import com.example.main.service.BookService;
import com.example.main.util.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public Book addBookInfo(Book book){
        return bookDao.save(book);
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

    @Override
    public Page<BookDto> getBooksByTag(Integer page, String tag) {
        List<String> tags = bookDao.getRelatedTags(tag);
        tags.add(tag);
        return bookDao.getBooksByTag(page,tags);
    }

    @Override
    public Book getBookByName(String name) {
        return bookDao.getBookByName(name);
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

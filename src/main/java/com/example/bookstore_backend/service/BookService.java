package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.BookSalesDto;
import com.example.bookstore_backend.dto.ExtendBookDto;
import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    public Page<BookDto> getBooks(Integer page, String value);
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value);
    public Integer getBooksPages();
    public void addBookInfo(Book book);
    public Optional<Book> getBookByBid(Long bid);
//    public List<BookDto> getBooksByRanks(Integer number);
//    public void increaseBookSales(Long bid, Integer increase);
    public Integer deleteBookByBid(Long bid);
    public Boolean modifyBookPic(String pic, Long bid);
    public Integer modifyBookInfo(ExtendBookDto dto);
    public List<BookSalesDto> getRanking(String startString, String endString, Integer limit, Long uid);
}

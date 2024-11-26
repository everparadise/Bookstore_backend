package com.example.main.service;

import com.example.main.dto.BookSalesDto;
import com.example.main.dto.ExtendBookDto;
import com.example.main.dto.BookDto;
import com.example.main.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    public Page<BookDto> getBooks(Integer page, String value);
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value);
    public Integer getBooksPages();
    public Book addBookInfo(Book book);
    public Optional<Book> getBookByBid(Long bid);
//    public List<BookDto> getBooksByRanks(Integer number);
//    public void increaseBookSales(Long bid, Integer increase);
    public Integer deleteBookByBid(Long bid);
    public Boolean modifyBookPic(String pic, Long bid);
    public Integer modifyBookInfo(ExtendBookDto dto);
    public List<BookSalesDto> getRanking(String startString, String endString, Integer limit, Long uid);

    public Page<BookDto> getBooksByTag(Integer page, String tag);
}

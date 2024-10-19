package com.example.bookstore_backend.dao;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.dto.ExtendBookDto;
import com.example.bookstore_backend.model.Book;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookDao {
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value);
    public Page<BookDto> getBooks(Integer page, String value);
    public Integer getBooksPage();
    public Integer increaseBookSales(Long bid, Integer increase);
    public Integer deleteBookByBid(Long bid);
    public void save(Book book);
    public Optional<Book> getBookByBid(Long bid);
    public void modifyBookStock(Long bid, Integer decrease) throws Exception;
    public Integer modifyBookPic(String pic, Long bid);
    public Integer modifyBookInfo(Long bid, String name, String author, String pic, String isbn, Integer stock, String comment);
}

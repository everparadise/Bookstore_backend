package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;

import java.util.List;

public interface BookRepository {
    public Book getBookByBid(Integer bid);
    public List<BookDto> getBookByPageNumber(Integer page);
    public Integer getBooksPages();
    public Boolean AddBookInfo(Book book);
    public List<Book> getBooksByRanks(Integer number);
    public Boolean increaseBookSales(Integer bid, Integer increase);
    public Boolean deleteBookByBid(Integer bid);
}

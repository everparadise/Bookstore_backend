package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    public List<BookDto> getBooksByPage(Integer page);
    public Integer getBooksPages();
    public Boolean addBookInfo(Book book);
    public Optional<Book> getBookByBid(Long bid);
    public List<BookDto> getBooksByRanks(Integer number);
    public Boolean increaseBookSales(Long bid, Integer increase);
    public Boolean deleteBookByBid(Long bid);
}

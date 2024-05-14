package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.impl.BookRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    public List<BookDto> getBooksByPage(Integer page);
    public Integer getBooksPages();
    public Boolean addBookInfo(Book book);
    public Book getBookByBid(Integer bid);
    public List<BookDto> getBooksByRanks(Integer number);
    public Boolean increaseBookSales(Integer bid, Integer increase);
    public Boolean deleteBookByBid(Integer bid);
}

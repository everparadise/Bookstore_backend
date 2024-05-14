package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.impl.BookRepositoryImpl;
import com.example.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    BookRepositoryImpl bookRepository;
    @Autowired
    public BookServiceImpl(BookRepositoryImpl bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getBooksByPage(Integer page) {
        return bookRepository.getBookByPageNumber(page);
    }

    @Override
    public Integer getBooksPages() {
        return bookRepository.getBooksPages();
    }

    @Override
    public Book getBookByBid(Integer bid) {
        return bookRepository.getBookByBid(bid);
    }

    @Override
    public Boolean addBookInfo(Book book){
        bookRepository.AddBookInfo(book);
        return true;
    }

    @Override
    public List<BookDto> getBooksByRanks(Integer number) {
        return bookRepository.getBooksByRanks(number).stream().map(BookServiceImpl::mapToBookDto).toList();
    }

    @Override
    public Boolean increaseBookSales(Integer bid, Integer increase) {
        bookRepository.increaseBookSales(bid, increase);
        return true;
    }

    @Override
    public Boolean deleteBookByBid(Integer bid) {
        bookRepository.deleteBookByBid(bid);
        return true;
    }

    private static BookDto mapToBookDto(Book book){
        return BookDto.builder()
                .bid(book.getBid())
                .pic(book.getPic())
                .price(book.getPrice())
                .name(book.getName())
                .sales(book.getSales())
                .build();

    }
}

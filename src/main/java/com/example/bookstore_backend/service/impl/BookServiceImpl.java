package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.BookRepository;
import com.example.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getBooksByPage(Integer page) {

        Pageable pageable = PageRequest.of(page * 12, (page + 1) * 12, Sort.by(Sort.Direction.ASC, "bid"));
        return bookRepository.getBooksByPageable(pageable);
    }

    @Override
    public Integer getBooksPages() {
        return bookRepository.getBooksPages();
    }

    @Override
    public Optional<Book> getBookByBid(Long bid) {
        return bookRepository.getBookByBid(bid);
    }

    @Override
    public Boolean addBookInfo(Book book){
        bookRepository.save(book);
        return true;
    }

    @Override
    public List<BookDto> getBooksByRanks(Integer number) {
        Pageable pageable = PageRequest.of(0, number, Sort.by(Sort.Direction.DESC, "sales"));
        return bookRepository.getBooksByPageable(pageable);
    }

    @Override
    public Boolean increaseBookSales(Long bid, Integer increase) {
        bookRepository.increaseBookSales(bid, increase);
        return true;
    }

    @Override
    public Boolean deleteBookByBid(Long bid) {
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

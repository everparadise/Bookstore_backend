package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> getBookByBid(Long bid);

    @Query("SELECT COUNT(book) FROM Book book")
    public Integer getBooksPages();
    @Query("SELECT new com.example.bookstore_backend.dto.BookDto(books.bid, books.pic, books.name,books.price, books.sales) FROM Book books")
    public List<BookDto> getBooksByPageable(Pageable pageable);

    @Modifying
    @Query("UPDATE Book book SET book.sales = book.sales + :increase WHERE book.bid = :bid")
    public Integer increaseBookSales(Long bid, Integer increase);
    public Integer deleteBookByBid(Long bid);
}

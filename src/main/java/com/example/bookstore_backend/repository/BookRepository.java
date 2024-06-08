package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.dto.ExtendBookDto;
import com.example.bookstore_backend.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> getBookByBid(Long bid);

    @Query("SELECT COUNT(book) FROM Book book")
    public Integer getBooksPages();
    @Query("SELECT new com.example.bookstore_backend.dto.ExtendBookDto(books.bid, books.pic, " +
            "books.name,books.price, books.sales, books.author, books.isbn, books.stock, books.comment)" +
            " FROM Book books WHERE books.name LIKE :value")
    public Page<ExtendBookDto> getExtendBooksByPageable(Pageable pageable, String value);

    @Query("SELECT new com.example.bookstore_backend.dto.BookDto(books.bid, books.pic, " +
            "books.name,books.price, books.sales)" +
            " FROM Book books WHERE books.name LIKE :value")
    public Page<BookDto> getBooksByPageable(Pageable pageable, String value);
    @Modifying
    @Query("UPDATE Book book SET book.sales = book.sales + :increase WHERE book.bid = :bid")
    public Integer increaseBookSales(Long bid, Integer increase);

    @Transactional
    @Modifying
    public Integer deleteBookByBid(Long bid);

    @Modifying
    @Transactional
    @Query("UPDATE Book book Set book.stock = :changing + book.stock WHERE book.bid = :bid")
    public void modifyBookStock(Long bid, Integer changing);

    @Modifying
    @Transactional
    @Query("UPDATE Book book SET book.pic = :pic WHERE book.bid = :bid")
    public Integer modifyBookPic(String pic, Long bid);

    @Modifying
    @Transactional
    @Query("UPDATE Book book SET book.name = :name, book.author = :author, book.pic = :pic," +
            " book.isbn = :isbn, book.stock = :stock, book.comment = :comment " +
            "WHERE book.bid = :bid ")
    Integer updateBookInfo(Long bid, String name, String author, String pic, String isbn, Integer stock, String comment);


}

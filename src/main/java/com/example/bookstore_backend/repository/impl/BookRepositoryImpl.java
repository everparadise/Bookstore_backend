package com.example.bookstore_backend.repository.impl;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    JdbcTemplate jdbcTemplate;



    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Book getBookByBid(Integer bid){
        String sql = "SELECT * FROM books WHERE bid = ?";

        List<Book> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), bid);
        return list.get(0);
    }

    @Override
    public List<BookDto> getBookByPageNumber(Integer page) {
        String sql = "SELECT bid, name, pic, price FROM books WHERE bid > ? ORDER BY bid LIMIT 12";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BookDto.class), page * CONSTANTS.booksPerPage);
    }

    @Override
    public Integer getBooksPages(){
        return jdbcTemplate.getMaxRows() / CONSTANTS.booksPerPage + 1;
    }

    @Override
    public Boolean AddBookInfo(Book book){
        String sql = "INSERT INTO books (name, comment, pic, author, price, stock, tag, sales, isbn) VALUES (?, ?, ?, ? ,?, ? ,?, ?, ?)";
        jdbcTemplate.update(sql, book.getName(), book.getComment(), book.getPic(), book.getAuthor(), book.getPrice(), book.getStock(), book.getTag(), book.getSales(), book.getIsbn());
        return true;
    }

    @Override
    public List<Book> getBooksByRanks(Integer number) {
        String sql = "SELECT * FROM books ORDER BY books.sales DESC  LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), number);
    }

    @Override
    public Boolean increaseBookSales(Integer bid, Integer increase) {
        String sql = "UPDATE books SET books.sales = sales + ? WHERE bid = ?";
        jdbcTemplate.update(sql, increase, bid);
        return true;
    }

    @Override
    public Boolean deleteBookByBid(Integer bid) {
        String sql = "DELETE FROM books WHERE bid = ?";
        jdbcTemplate.update(sql, bid);
        return true;
    }
}

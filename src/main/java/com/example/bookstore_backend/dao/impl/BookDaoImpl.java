package com.example.bookstore_backend.dao.impl;

import com.example.bookstore_backend.dao.BookDao;
import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.dto.ExtendBookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    BookRepository bookRepository;
    @Autowired
    BookDaoImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value) {
        Pageable pageable = PageRequest.of(page,  12, Sort.by(Sort.Direction.ASC, "bid"));
        value = "%" + value + "%";
        return bookRepository.getExtendBooksByPageable(pageable, value);
    }


    @Override
    public Page<BookDto> getBooks(Integer page, String value) {
        Pageable pageable = PageRequest.of(page,  12, Sort.by(Sort.Direction.ASC, "bid"));
        value = "%" + value + "%";
        return bookRepository.getBooksByPageable(pageable, value);
    }


    @Override
    public Integer getBooksPage() {
        return bookRepository.getBooksPages();
    }

    @Override
    public Integer increaseBookSales(Long bid, Integer increase) {

        return bookRepository.increaseBookSales(bid, increase);
    }

    @Override
    public Integer deleteBookByBid(Long bid) {
        return bookRepository.deleteBookByBid(bid);

    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Optional<Book> getBookByBid(Long bid) {
        return bookRepository.getBookByBid(bid);
    }

    @Override
    public void modifyBookStock(Long bid, Integer changing) throws Exception {
        Integer stock = bookRepository.getStockByBid(bid);
        if(stock < changing) throw new Exception("目标书籍 \"" + bookRepository.getNameByBid(bid) + "\" 余量不足");
        bookRepository.modifyBookStock(bid, changing);
    }

    @Override
    public Integer modifyBookPic(String pic, Long bid) {
        return bookRepository.modifyBookPic(pic, bid);
    }

    @Override
    public Integer modifyBookInfo(Long bid, String name, String author, String pic, String isbn, Integer stock, String comment) {
        return bookRepository.updateBookInfo(bid, name, author, pic, isbn, stock, comment);
    }
}

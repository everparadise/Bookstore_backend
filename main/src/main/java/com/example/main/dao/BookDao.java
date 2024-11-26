package com.example.main.dao;

import com.example.main.dto.BookDto;
import com.example.main.dto.ExtendBookDto;
import com.example.main.model.Book;
import com.example.main.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value);
    public Page<BookDto> getBooks(Integer page, String value);
    public Integer getBooksPage();
    public Integer increaseBookSales(Long bid, Integer increase);
    public Integer deleteBookByBid(Long bid);
    public Book save(Book book);
    public Optional<Book> getBookByBid(Long bid);
    public void modifyBookStock(Long bid, Integer decrease) throws Exception;
    public Integer modifyBookPic(String pic, Long bid);
    public Integer modifyBookInfo(Long bid, String name, String author, String pic, String isbn, Integer stock, String comment);

    public List<String> getRelatedTags(String tagName);
    public Page<BookDto> getBooksByTag(Integer page, List<String> tags);
}

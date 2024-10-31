package com.example.author.dao.impl;

import com.example.author.constants.RedisConstants;
import com.example.author.dao.BookDao;
import com.example.author.repository.BookRepository;
import com.example.author.util.CacheClient;
import com.example.author.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CacheClient cacheClient;

    private Optional<Book> fallbackForGetBookByBid(Long bid){
        return bookRepository.getBookByBid(bid);
    }
    private List<Long> fallbackForGetBidByName(String name) {
        return bookRepository.getBidByName(name);
    }

    @Override
    public List<String> getBookAuthorByName(String name) {
        System.out.println(name);
        List<Long> list = fallbackForGetBidByName(name);
        return list.stream().map(bid -> {
            return cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX, bid, Book.class, this::fallbackForGetBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES).get().getAuthor();
        }).toList();
    }
}

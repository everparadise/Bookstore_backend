package com.example.bookstore_backend.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.example.bookstore_backend.Constants.RedisConstants;
import com.example.bookstore_backend.dao.BookDao;
import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.dto.ExtendBookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.repository.BookRepository;
import com.example.bookstore_backend.util.BookCacheClient;
import com.example.bookstore_backend.util.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class BookDaoImpl implements BookDao {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CacheClient cacheClient;

    @Override
    public Page<ExtendBookDto> getExtendBooks(Integer page, String value) {
        Pageable pageable = PageRequest.of(page,  12, Sort.by(Sort.Direction.ASC, "bid"));
        value = "%" + value + "%";

        Page<Long> bookIdPage =  bookRepository.getExtendBooksByPageable(pageable, value);
        return bookIdPage.map(bookId -> {
            Optional<Book> opTarget = cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX, bookId, Book.class, bookRepository::getBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
            return opTarget.map(ExtendBookDto::new).orElse(null);
        });
    }


    @Override
    public Page<BookDto> getBooks(Integer page, String value) {
        Pageable pageable = PageRequest.of(page,  12, Sort.by(Sort.Direction.ASC, "bid"));
        value = "%" + value + "%";
        Page<Long> bookIdPage = bookRepository.getBooksByPageable(pageable, value);
        return bookIdPage.map(bookId -> {
            Optional<Book> opTarget = cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_NUMBER_KEY_PREFIX, bookId, Book.class, bookRepository::getBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
            return opTarget.map(BookDto::new).orElse(null);
        });
    }


    @Override
    public Integer getBooksPage() {
        try{
            String result = stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_BOOKS_NUMBER_KEY_PREFIX);
            if(result == null || StrUtil.isAllBlank(result))
                throw(new Exception("null"));
            System.out.println("redis pages hit:" + result);
            return Integer.parseInt(result);
        }catch (Exception e){
            System.out.println("repository fallback -> get books pages");
            Integer result = bookRepository.getBooksPages();
            try{
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_BOOKS_NUMBER_KEY_PREFIX, result.toString(), RedisConstants.CACHE_BOOK_NUMBER_TTL, TimeUnit.HOURS);
                System.out.println("redis -> set books pages = " + result);
            }catch (Exception newException){
                System.out.println("redis error");
                return result;
            }
            return result;
        }
    }

    @Override
    public Integer increaseBookSales(Long bid, Integer increase) {

        return bookRepository.increaseBookSales(bid, increase);
    }

    @Override
    public Integer deleteBookByBid(Long bid) {
        Integer res = bookRepository.deleteBookByBid(bid);
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + bid);

        try {
            Thread.sleep(500);  // 延时 0.5 s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("double delete strategy");
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX);
        return res;
    }

    @Override
    public void save(Book book) {
        Book result = bookRepository.save(book);
        cacheClient.setRedis(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + result.getBid(), result, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
    }

    @Override
    public Optional<Book> getBookByBid(Long bid) {
        return cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX, bid, Book.class, this::fallbackForGetBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
    }

    private Optional<Book> fallbackForGetBookByBid(Long bid){
        return bookRepository.getBookByBid(bid);
    }

    @Override
    public void modifyBookStock(Long bid, Integer changing) throws Exception {
        Optional<Book> opBook = cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX, bid, Book.class, this::fallbackForGetBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
        if(opBook.isEmpty()) throw new Exception("目标书籍不存在");
        Integer stock = opBook.get().getStock();
        if(stock < changing) throw new Exception("目标书籍 \"" + bookRepository.getNameByBid(bid) + "\" 余量不足");
        bookRepository.modifyBookStock(bid, changing);
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + bid);
        try {
            Thread.sleep(500);  // 延时 0.5 s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("double delete strategy");
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX);
    }

    @Override
    public Integer modifyBookPic(String pic, Long bid) {
        Integer tmp = bookRepository.modifyBookPic(pic, bid);
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + bid);
        return tmp;
    }

    @Override
    public Integer modifyBookInfo(Long bid, String name, String author, String pic, String isbn, Integer stock, String comment) {
        Integer tmp = bookRepository.updateBookInfo(bid, name, author, pic, isbn, stock, comment);
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + bid);
        return tmp;
    }
}

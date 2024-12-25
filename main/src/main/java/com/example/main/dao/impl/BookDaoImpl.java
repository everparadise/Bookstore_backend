package com.example.main.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.example.main.Constants.RedisConstants;
import com.example.main.dao.BookDao;
import com.example.main.dto.BookDto;
import com.example.main.dto.ExtendBookDto;
import com.example.main.model.Book;
import com.example.main.model.Tag;
import com.example.main.repository.BookPicRepository;
import com.example.main.repository.BookRepository;
import com.example.main.repository.TagRepository;
import com.example.main.util.CacheClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@EnableNeo4jRepositories
public class BookDaoImpl implements BookDao {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookPicRepository bookPicRepository;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CacheClient cacheClient;
    @Autowired
    TagRepository tagRepository;

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
        cacheClient.safeDelete(RedisConstants.CACHE_BOOKS_BOOK_KEY_PREFIX + bid);
        Integer res = bookRepository.deleteBookByBid(bid);
        bookPicRepository.deleteBookPicByBid(bid);

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
    public Book save(Book book) {
        return bookRepository.save(book);
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

    @Override
    public List<String> getRelatedTags(String tagName) {
         System.out.println("TagRepository is null: " + (tagRepository == null));
         return tagRepository.findRelatedTags(tagName);
    }

    @Override
    public Page<BookDto> getBooksByTag(Integer page, List<String> tags) {
        System.out.println("getBooksByTag");
        Pageable pageable = PageRequest.of(page,  12, Sort.by(Sort.Direction.ASC, "bid"));
        Page<Long> bookIdPage = bookRepository.getBooksByPageableAndTags(pageable, tags);
        return bookIdPage.map(bookId -> {
            Optional<Book> opTarget = cacheClient.queryRedis(RedisConstants.CACHE_BOOKS_NUMBER_KEY_PREFIX, bookId, Book.class, bookRepository::getBookByBid, RedisConstants.CACHE_BOOK_TTL, TimeUnit.MINUTES);
            return opTarget.map(BookDto::new).orElse(null);
        });
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepository.getBookByName(name);
    }
}

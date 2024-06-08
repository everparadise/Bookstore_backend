package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dao.BookDao;
import com.example.bookstore_backend.dao.CartDao;
import com.example.bookstore_backend.dao.UserDao;
import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.dto.NewCartDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.BookRepository;
import com.example.bookstore_backend.repository.CartbookRepository;
import com.example.bookstore_backend.repository.UserRepository;
import com.example.bookstore_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartServiceImpl implements CartService {
    CartDao cartDao;
    UserDao userDao;
    BookDao bookDao;
    @Autowired
    public CartServiceImpl(CartDao cartDao, UserDao userDao, BookDao bookDao){
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }
    @Override
    public Boolean AddCartItem(NewCartDto dto, Long uid) {
        if(dto.getNumbers() == null) dto.setNumbers(1);
        User user = userDao.getUserByUid(uid).orElseThrow(()-> new NoSuchElementException("No target User"));
        Book book = bookDao.getBookByBid(dto.getBid()).orElseThrow(()->new NoSuchElementException("No target Book"));

        CartBook cartBook = CartBook.builder()
                .book(book)
                .user(user)
                .dateTime(LocalDateTime.now())
                .number(dto.getNumbers())
                .build();

        cartDao.save(cartBook);
        return true;
    }


    @Override
    public List<CartBookDto> getCartItemByUid(Long uid) {

        return cartDao.getCartBookByUid(uid).stream().map(this::mapToCartDto).toList();
    }

    @Override
    public Boolean deleteCartItem(Long cid) {
        cartDao.deleteCartBookByCid(cid);
        return true;
    }

    public CartBookDto mapToCartDto(CartBook cartBook){
//        User user = userRepository.getUserByUid(cartBook.getUid()).get();
        Book book = cartBook.getBook();
        User user = cartBook.getUser();
        return CartBookDto.builder()
                .price(book.getPrice())
                .bid(book.getBid())
                .pic(book.getPic())
                .cid(cartBook.getCid())
                .name(book.getName())
                .uid(user.getUid())
                .comment(book.getComment())
                .number(cartBook.getNumber())
                .build();
    }
}

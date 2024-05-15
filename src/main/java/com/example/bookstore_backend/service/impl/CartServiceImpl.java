package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.BookRepository;
import com.example.bookstore_backend.repository.CartbookRepository;
import com.example.bookstore_backend.repository.UserRepository;
import com.example.bookstore_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    CartbookRepository cartbookRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    @Autowired
    public CartServiceImpl(CartbookRepository cartbookRepository, UserRepository userRepository, BookRepository bookRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.cartbookRepository = cartbookRepository;
    }
    @Override
    public Boolean AddCartItem(CartBook book) {
        book.setSelected(false);
        if(book.getNumber() == null) book.setNumber(1);
        if(book.getSelected() == null) book.setSelected(false);
        cartbookRepository.save(book);
        return null;
    }

    @Override
    public Boolean updateCartItem(CartBook book) {
        cartbookRepository.save(book);
        return true;
    }

    @Override
    public List<CartBookDto> getCartItemByUid(Long uid) {
        return cartbookRepository.getCartbookByUid(uid).stream().map(this::mapToCartDto).toList();
    }

    @Override
    public Boolean deleteCartItem(Long cid) {
        cartbookRepository.deleteCartBookByCid(cid);
        return true;
    }

    public CartBookDto mapToCartDto(CartBook cartBook){
//        User user = userRepository.getUserByUid(cartBook.getUid()).get();
        Book book = bookRepository.getBookByBid(cartBook.getBid()).get();
        return CartBookDto.builder()
                .price(book.getPrice())
                .bid(cartBook.getBid())
                .pic(book.getPic())
                .cid(cartBook.getCid())
                .name(book.getName())
                .uid(cartBook.getUid())
                .comment(book.getComment())
                .selected(cartBook.getSelected())
                .number(cartBook.getNumber())
                .build();
    }
}

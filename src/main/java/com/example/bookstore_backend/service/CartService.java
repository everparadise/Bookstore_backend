package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.dto.NewCartDto;
import com.example.bookstore_backend.model.CartBook;

import java.util.List;

public interface CartService {
    public List<CartBookDto> getCartItemByUid(Long uid);
    public Boolean AddCartItem(NewCartDto book, Long uid);
    public Boolean deleteCartItem(Long cid);
}

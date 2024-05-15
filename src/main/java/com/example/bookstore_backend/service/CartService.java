package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.CartBook;

import java.util.List;

public interface CartService {
    public List<CartBookDto> getCartItemByUid(Long uid);
    public Boolean AddCartItem(CartBook book);
    public Boolean updateCartItem(CartBook book);
    public Boolean deleteCartItem(Long cid);
}

package com.example.main.service;

import com.example.main.dto.CartBookDto;
import com.example.main.dto.NewCartDto;

import java.util.List;

public interface CartService {
    public List<CartBookDto> getCartItemByUid(Long uid);
    public Boolean AddCartItem(NewCartDto book, Long uid);
    public Boolean deleteCartItem(Long cid);
}

package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.CartBook;

import java.util.List;

public interface CartbookRepository {
    List<CartBookDto> getCartbookByUid(Integer uid);
    Boolean deleteCartItemInfo(Integer cid);
    Boolean updateCartItemInfo(CartBook book);
    Boolean addCartItemInfo(CartBook book);
}

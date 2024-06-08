package com.example.bookstore_backend.dao;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.CartBook;

import java.util.List;

public interface CartDao {
    public void deleteCartBookByCid(Long cid);
    List<CartBook> getCartBookByUid(Long uid);
    public void save(CartBook cartBook);
}

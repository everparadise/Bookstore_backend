package com.example.main.dao;

import com.example.main.model.CartBook;

import java.util.List;

public interface CartDao {
    public void deleteCartBookByCid(Long cid);
    List<CartBook> getCartBookByUid(Long uid);
    public void save(CartBook cartBook);
}

package com.example.main.dao.impl;

import com.example.main.dao.CartDao;
import com.example.main.model.CartBook;
import com.example.main.repository.CartbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDaoImpl implements CartDao {
    CartbookRepository cartbookRepository;
    @Autowired
    CartDaoImpl(CartbookRepository cartbookRepository){
        this.cartbookRepository = cartbookRepository;
    }

    @Override
    public void deleteCartBookByCid(Long cid) {
        cartbookRepository.deleteCartBookByCid(cid);
    }

    @Override
    public List<CartBook> getCartBookByUid(Long uid) {
        return cartbookRepository.findByUser_Uid(uid);
    }

    @Override
    public void save(CartBook cartBook) {
        cartbookRepository.save(cartBook);
    }
}

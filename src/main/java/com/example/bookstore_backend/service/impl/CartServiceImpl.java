package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.repository.impl.CartbookRepositoryImpl;
import com.example.bookstore_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    CartbookRepositoryImpl cartbookRepository;
    @Autowired
    public CartServiceImpl(CartbookRepositoryImpl cartbookRepository){
        this.cartbookRepository = cartbookRepository;
    }
    @Override
    public Boolean AddCartItem(CartBook book) {
        book.setSelected(false);
        if(book.getNumber() == null) book.setNumber(1);
        if(book.getSelected() == null) book.setSelected(false);
        cartbookRepository.addCartItemInfo(book);
        return null;
    }

    @Override
    public Boolean updateCartItem(CartBook book) {
        cartbookRepository.updateCartItemInfo(book);
        return true;
    }

    @Override
    public List<CartBookDto> getCartItemByUid(Integer uid) {
        return cartbookRepository.getCartbookByUid(uid);
    }

    @Override
    public Boolean deleteCartItem(Integer cid) {
        cartbookRepository.deleteCartItemInfo(cid);
        return true;
    }
}

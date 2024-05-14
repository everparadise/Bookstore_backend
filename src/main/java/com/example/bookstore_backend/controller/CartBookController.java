package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.repository.CartbookRepository;
import com.example.bookstore_backend.repository.impl.CartbookRepositoryImpl;
import com.example.bookstore_backend.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cart")
public class CartBookController {
    CartServiceImpl cartService;
    @Autowired
    public CartBookController(CartServiceImpl cartService){
        this.cartService = cartService;
    }

    @GetMapping("/items/{userId}")
    public ResponseDto<List<CartBookDto>> getCartItemByUid(@PathVariable Integer userId){
        List<CartBookDto> list = cartService.getCartItemByUid(userId);
        if(!list.isEmpty()){
            return new ResponseDto<>(true, "Operation OK", list);
        }
        else return new ResponseDto<>(true, "No CartItem Found", list);
    }

    @PostMapping("/addItem")
    public ResponseDto<CartBook> addCartItemInfo(@RequestBody CartBook cartbook){

        cartService.AddCartItem(cartbook);
        return new ResponseDto<>(true, "Operation OK", cartbook);
    }

    @PutMapping("/modifyCart")
    public ResponseDto<Boolean> updateCartItemInfo(@RequestBody CartBook cartbook){
        cartService.updateCartItem(cartbook);
        return new ResponseDto<>(true, "Operation OK", true);
    }

    @DeleteMapping("/deleteItem/{cid}")
    public ResponseDto<Boolean> deleteCartItemInfo(@PathVariable Integer cid){
        cartService.deleteCartItem(cid);
        return new ResponseDto<>(true, "Operation OK", true);
    }
}

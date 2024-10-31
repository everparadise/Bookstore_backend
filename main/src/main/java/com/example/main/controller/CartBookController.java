package com.example.main.controller;

import com.example.main.dto.CartBookDto;
import com.example.main.dto.NewCartDto;
import com.example.main.dto.ResponseDto;
import com.example.main.service.CartService;
import com.example.main.util.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("v1/cart")
public class CartBookController {
    CartService cartService;
    @Autowired
    public CartBookController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseDto<List<CartBookDto>> getCartItemByUid(){
        Long userId = UserProvider.getUserUid();
        List<CartBookDto> list = cartService.getCartItemByUid(userId);
        if(!list.isEmpty()){
            return new ResponseDto<>(true, "Operation OK", list);
        }
        else return new ResponseDto<>(true, "No CartItem Found", list);
    }

    @PostMapping
    public ResponseDto<Boolean> addCartItemInfo(@RequestBody NewCartDto dto){
        try{
            cartService.AddCartItem(dto, UserProvider.getUserUid());
        }catch(NoSuchElementException e){
            return new ResponseDto<>(false, e.getLocalizedMessage(), false);
        }

        return new ResponseDto<>(true, "Operation OK", true);
    }


    @DeleteMapping("/{cid}")
    public ResponseDto<Boolean> deleteCartItemInfo(@PathVariable Long cid){
        cartService.deleteCartItem(cid);
        return new ResponseDto<>(true, "Operation OK", true);
    }
}

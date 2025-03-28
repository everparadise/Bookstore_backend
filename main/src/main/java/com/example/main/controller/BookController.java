package com.example.main.controller;

import com.example.main.Constants.Role;
import com.example.main.dto.*;
import com.example.main.model.Book;
import com.example.main.service.BookService;
import com.example.main.util.UserProvider;
import com.example.main.dto.BookRequest;
import com.example.main.dto.ExtendBookDto;
import com.example.main.dto.ResponseDto;
import com.example.main.dto.SimplifiedPageDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/book")
public class BookController {
    BookService bookServiceImpl;

    @Autowired
    public BookController(BookService bookServiceImpl){
        this.bookServiceImpl = bookServiceImpl;
    }

    @PostMapping("books")
    public ResponseDto<SimplifiedPageDto<?>> getBooksByPageNumber(@RequestBody BookRequest request){
        Page<?> pages;
        Role role = UserProvider.getUserRole();
        if(role.equals(Role.ADMIN)){
            pages = bookServiceImpl.getExtendBooks(request.getPage(), request.getValue());
        }
        else{
            pages = bookServiceImpl.getBooks(request.getPage(), request.getValue());
        }
        return new ResponseDto<>(true, role.toString() , new SimplifiedPageDto<>(
                pages.getContent(),
                pages.getTotalPages(),
                (int) pages.getTotalElements()));
        }

    @GetMapping("booksPages")
    public ResponseDto<Integer> getBooksNumbers(){
        return new ResponseDto<>(true, "Operation OK", bookServiceImpl.getBooksPages());
    }

    @GetMapping("{bid}")
    public ResponseDto<Book> getBookByBid(@PathVariable Long bid){
        Optional<Book> book = bookServiceImpl.getBookByBid(bid);
        if(book.isPresent()){
            return new ResponseDto<>(true, "Operation OK", book.get());
        }
        else return new ResponseDto<>(false, "bid {%d} book not found".formatted(bid), null);
    }

    @PostMapping
    public ResponseDto<Book> addBookInfo(@RequestBody Book book){
        //根据isbn号去重
        book.setSales(0);
        Book result = bookServiceImpl.addBookInfo(book);
        return new ResponseDto<>(true, "Operation OK", result);
    }

    @PutMapping
    public ResponseDto<?> modifyBookInfo(@RequestBody ExtendBookDto bookDto){
        Integer result = bookServiceImpl.modifyBookInfo(bookDto);
        if(result != 0)
            return new ResponseDto<>(true, "Operation OK", bookDto);
        else return new ResponseDto<>(false, "Update Fault", null);
    }

    @PutMapping("pic/{pic}/{bid}")
    public ResponseDto<Boolean> modifyBookPic(@PathVariable String pic, @PathVariable Long bid){
        if(bookServiceImpl.modifyBookPic(pic, bid)){
            return new ResponseDto<>(true, "Operation OK", true);
        }else return new ResponseDto<>(false, "bid not found", false);
    }

    @DeleteMapping("{bid}")
    public ResponseDto<Boolean> deleteBookByBid(@PathVariable Long bid){
        bookServiceImpl.deleteBookByBid(bid);
        return new ResponseDto<>(true, "Operation OK", true);
    }

    @PostMapping("tags")
    public ResponseDto<SimplifiedPageDto<?>> getBooksByTag(@RequestBody BookRequest bookRequest){
        String tag = bookRequest.getValue();
        Integer page = bookRequest.getPage();
        System.out.println("tag: " + tag + " page: " + page);
        Page<?> pages = bookServiceImpl.getBooksByTag(page, tag);
        return new ResponseDto<>(true, "Operation OK", new SimplifiedPageDto<>(
                pages.getContent(),
                pages.getTotalPages(),
                (int) pages.getTotalElements()));
    }


}

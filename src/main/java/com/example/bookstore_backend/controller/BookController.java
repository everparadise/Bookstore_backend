package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.BookDto;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/book")
public class BookController {
    BookService bookServiceImpl;

    @Autowired
    public BookController(BookService bookServiceImpl){
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("books/{page}")
    public ResponseDto<List<BookDto>> getBooksByPageNumber(@PathVariable Integer page){
        List<BookDto> list = bookServiceImpl.getBooksByPage(page);
        if(!list.isEmpty()){
            return new ResponseDto<>(true, "Operation OK", list);
        }
        return new ResponseDto<>(false, "Operation failure", null);
    }

    @GetMapping("booksPages")
    public ResponseDto<Integer> getBooksNumbers(){
        return new ResponseDto<>(true, "Operation OK", bookServiceImpl.getBooksPages());
    }

    @GetMapping("book/{bid}")
    public ResponseDto<Book> getBookByBid(@PathVariable Long bid){
        Optional<Book> book = bookServiceImpl.getBookByBid(bid);
        if(book.isPresent()){
            return new ResponseDto<>(true, "Operation OK", book.get());
        }
        else return new ResponseDto<>(false, "bid {%d} book not found".formatted(bid), null);
    }

    @PostMapping("addBook")
    public ResponseDto<Boolean> addBookInfo(@RequestBody Book book){
        //根据isbn号去重
        book.setSales(0);
        bookServiceImpl.addBookInfo(book);
        return new ResponseDto<>(true, "Operation OK", true);
    }

    @GetMapping("ranking")
    public ResponseDto<List<BookDto>> getTopRankBooks(@RequestParam(value = "number",defaultValue = "10") Integer number ){
        List<BookDto> list = bookServiceImpl.getBooksByRanks(number);
        if(!list.isEmpty()){
            return new ResponseDto<>(true, "Operation OK", list);
        }
        else return new ResponseDto<>(false, "search failure", null);
    }

    @DeleteMapping("delete/{bid}")
    public ResponseDto<Boolean> deleteBookByBid(@PathVariable Long bid){
        bookServiceImpl.deleteBookByBid(bid);
        return new ResponseDto<>(true, "Operation OK", true);
    }
}

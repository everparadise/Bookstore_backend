package com.example.bookstore_backend.dto;

import com.example.bookstore_backend.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendBookDto {
    private Long bid;
    private String pic;
    private String name;
    private Integer price;
    private Integer sales;

    private String author;
    private String isbn;
    private Integer stock;
    private String comment;

    public ExtendBookDto(Book book) {
        bid = book.getBid();
        pic = book.getPic();
        name = book.getName();
        price = book.getPrice();
        sales = book.getSales();
        author = book.getAuthor();
        isbn = book.getIsbn();
        stock = book.getStock();
        comment = book.getComment();
    }
}

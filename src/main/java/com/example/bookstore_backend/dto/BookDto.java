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
public class BookDto {
    private Long bid;
    private String pic;
    private String name;
    private Integer price;
    private Integer sales;

    public BookDto(Book book) {
        this.bid = book.getBid();
        this.pic = book.getPic();
        this.name = book.getName();
        this.price = book.getPrice();
        this.sales = book.getSales();
    }
}

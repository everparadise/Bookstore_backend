package com.example.bookstore_backend.dto;

import com.example.bookstore_backend.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSalesDto {
    Long bid;
    String name;
    String pic;
    Integer price;
    Long sales;
}

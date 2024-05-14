package com.example.bookstore_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    Integer bid;
    String name;
    String pic;
    String comment;
    String author;
    Double price;
    Integer stock;
    String tag;
    Integer sales;
    String isbn;
}

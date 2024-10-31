package com.example.main.dto;

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

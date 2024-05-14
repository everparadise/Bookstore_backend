package com.example.bookstore_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartBook{
    Integer number;
    Integer bid;
    Integer cid;
    Integer uid;
    Boolean selected;
}

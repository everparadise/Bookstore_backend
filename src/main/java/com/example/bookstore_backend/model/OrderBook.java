package com.example.bookstore_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBook{
    Integer oid;
    Integer uid;
    Integer bid;
    LocalDateTime dateTime;
    Double totalPrice;
    String address;
    String telephone;
    Integer number;
}

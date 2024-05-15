package com.example.bookstore_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "orderbooks")
@Entity
public class OrderBook{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long oid;


    Long uid;

    Long bid;
    LocalDateTime dateTime;
    Double totalPrice;
    String address;
    String telephone;
    Integer number;
    String name;
}

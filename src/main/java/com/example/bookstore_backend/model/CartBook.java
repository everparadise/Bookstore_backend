package com.example.bookstore_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "cartbooks")
@Entity
public class CartBook{
    Integer number;

    Long uid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cid;

    Long bid;
    Boolean selected;
}

package com.example.bookstore_backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "books")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bid")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pic;

    private String comment;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer price;

    private Integer stock;

    private String tag;

    private Integer sales;

    @Column(nullable = false)
    private String isbn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderBook> orders;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartBook> carts;
}

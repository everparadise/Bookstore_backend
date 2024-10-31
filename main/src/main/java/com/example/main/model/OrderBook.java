package com.example.main.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

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

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "Oid")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Book book;

    @Column(nullable = false)
    private Integer number;
}

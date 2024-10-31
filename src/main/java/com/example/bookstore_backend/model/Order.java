package com.example.bookstore_backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Oid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @CreationTimestamp
    @Column(name = "order_number", nullable = false)
    private LocalDateTime date;

    @Column(name = "order_address", nullable = false)
    private  String address;

    @Column(name = "order_phone", nullable = false)
    private String phone;

    @Column(name = "order_receiver", nullable = false)
    private String receiver;

    @OneToMany(mappedBy = "order",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    List<OrderBook> items;

    @Column(name = "order_price", nullable = false)
    private Integer totalPrice;
}

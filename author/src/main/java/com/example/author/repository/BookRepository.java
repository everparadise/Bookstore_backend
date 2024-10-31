package com.example.author.repository;

import com.example.author.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> getBookByBid(Long bid);

    @Query("SELECT b.bid FROM Book b WHERE b.name = :name")
    List<Long> getBidByName(String name);
    List<Book> getAllByBidBetween(Long start, Long end);
}

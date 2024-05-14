package com.example.bookstore_backend.repository.impl;

import com.example.bookstore_backend.dto.CartBookDto;
import com.example.bookstore_backend.model.Book;
import com.example.bookstore_backend.model.CartBook;
import com.example.bookstore_backend.repository.CartbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartbookRepositoryImpl implements CartbookRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public CartbookRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<CartBookDto> getCartbookByUid(Integer uid){
        String sql = ("SELECT books.bid,books.name,books.price, books.pic, books.comment, cartbooks.number,cartbooks.selected, cartbooks.cid, cartbooks.uid" +
                " FROM cartbooks INNER JOIN books " +
                "ON cartbooks.bid = books.bid " +
                "WHERE cartbooks.uid = ?");
        RowMapper<CartBookDto> mapper = (rs, number) ->{
            return CartBookDto.builder()
                    .bid(rs.getInt(1))
                    .name(rs.getString(2))
                    .price(rs.getDouble(3))
                    .pic(rs.getString(4))
                    .comment(rs.getString(5))
                    .number(rs.getInt(6))
                    .selected(rs.getBoolean(7))
                    .cid(rs.getInt(8))
                    .uid(rs.getInt(9))
                    .build();
        };

        return jdbcTemplate.query(sql, mapper, uid);
    }

    @Override
    public Boolean addCartItemInfo(CartBook book){
        String sql = "INSERT INTO cartbooks (bid, uid, number, selected) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getBid(), book.getUid(), book.getNumber(), book.getSelected());
        return true;
    }

    @Override
    public Boolean updateCartItemInfo(CartBook book){
        String sql = "UPDATE cartbooks SET cartbooks.number = ? WHERE cid = ?";
        jdbcTemplate.update(sql, book.getNumber(), book.getCid());
        return true;
    }

    @Override
    public Boolean deleteCartItemInfo(Integer cid){
        String sql = "DELETE FROM cartbooks WHERE cid = ?";
        jdbcTemplate.update(sql, cid);
        return true;
    }
}

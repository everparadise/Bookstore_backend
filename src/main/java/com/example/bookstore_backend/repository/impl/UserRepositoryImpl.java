package com.example.bookstore_backend.repository.impl;

import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto save(User user){
        String sql = "insert into users ( slogan, username, password, remainMoney, avatar) values (?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getSlogan(), user.getUsername(), user.getPassword(), user.getRemainMoney(), user.getAvatar());
        String newsql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(newsql, new BeanPropertyRowMapper<>(UserDto.class), user.getUsername());
    }

    @Override
    public UserDto userInfoValidation(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? And password = ?";

        UserDto dto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), username, password);
        return dto;
    }

    @Override
    public User getUserByUid(Integer uid) {
        String sql = "SELECT * FROM users WHERE uid = ? LIMIT 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), uid).get(0);
    }

    @Override
    public UserDto updateUserInfo(UserDto userDto) {
        String sql = "UPDATE users SET slogan = ?, username = ?, avatar = ? WHERE uid = ?";
        jdbcTemplate.update(sql, userDto.getSlogan(), userDto.getUsername(), userDto.getAvatar(), userDto.getUid());
        return userDto;
    }
}

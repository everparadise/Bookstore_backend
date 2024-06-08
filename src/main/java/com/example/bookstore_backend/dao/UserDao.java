package com.example.bookstore_backend.dao;

import com.example.bookstore_backend.Constants.Role;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.dto.UserStateDto;
import com.example.bookstore_backend.model.User;

import java.util.Optional;

public interface UserDao {
    public Optional<User> getUserByUid(Long uid);
    public Integer PrivateSave(UserDto dto);
    public Integer decreaseMoney(Long uid, Integer cost);
    public Integer updateUserStatusByUid(Long uid, Role role);
    public Optional<UserStateDto> getUserStateByUsername(String username);
}

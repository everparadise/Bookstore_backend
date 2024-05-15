package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<UserDto> loginRequest(String username, String password);
    public void insertUserInfo(User user);
    public UserDto getUserByUid(Long uid);
    public UserDto updateUserInfo(UserDto user);
    public Boolean decreaseMoney(Long uid, Double spend);
}

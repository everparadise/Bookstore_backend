package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;

public interface UserService {
    public UserDto loginRequest(String username, String password);
    public UserDto insertUserInfo(User user);
    public UserDto getUserByUid(Integer uid);
    public UserDto updateUserInfo(UserDto user);
}

package com.example.bookstore_backend.repository;

import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public UserDto save(User user);
    public User getUserByUid(Integer uid);
    public UserDto updateUserInfo(UserDto userDto);
    public UserDto userInfoValidation(String username, String password);
}

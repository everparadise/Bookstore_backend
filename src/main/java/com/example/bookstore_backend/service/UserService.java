package com.example.bookstore_backend.service;

import com.example.bookstore_backend.dto.UserConsumingDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.dto.UserStateDto;
import com.example.bookstore_backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public UserDto getUserByUid(Long uid);
    public UserDto updateUserInfo(UserDto user);
    public Boolean banUserByUid(Long uid);
    public Boolean UnbanUserByUid(Long uid);
    public List<UserConsumingDto> getUserByRangingAndLimit(String startString, String endString, Integer limit);
    public UserStateDto getUserStateByUsername(String username);
}

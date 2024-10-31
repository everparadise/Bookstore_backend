package com.example.main.dao;

import com.example.main.Constants.Role;
import com.example.main.dto.UserDto;
import com.example.main.dto.UserStateDto;
import com.example.main.model.User;

import java.util.Optional;

public interface UserDao {
    public Optional<User> getUserByUid(Long uid);
    public Integer PrivateSave(UserDto dto);
    public Integer decreaseMoney(Long uid, Integer cost);
    public Integer updateUserStatusByUid(Long uid, Role role);
    public Optional<UserStateDto> getUserStateByUsername(String username);
}

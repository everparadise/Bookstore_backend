package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.impl.UserRepositoryImpl;
import com.example.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    UserRepositoryImpl userRepositoryImpl;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl){
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public UserDto loginRequest(String username, String password) {
        return userRepositoryImpl.userInfoValidation(username, password);
    }

    @Override
    public UserDto insertUserInfo(User user) {
        user.setAvatar(CONSTANTS.defaultAvatar);
        user.setRemainMoney(CONSTANTS.defaultMoney);
        userRepositoryImpl.save(user);
        return null;
    }

    @Override
    public UserDto getUserByUid(Integer uid) {
        return mapToUserDto(userRepositoryImpl.getUserByUid(uid));
    }

    @Override
    public UserDto updateUserInfo(UserDto user) {
        try {
            userRepositoryImpl.updateUserInfo(user);
        }catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                return null;
            }
        }
        return user;
    }

    private static UserDto mapToUserDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .remainMoney(user.getRemainMoney())
                .slogan(user.getSlogan())
                .uid(user.getUid())
                .build();
    }
}

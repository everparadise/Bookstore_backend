package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.UserRepository;
import com.example.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDto> loginRequest(String username, String password) {
        return userRepository.userInfoValidation(username, password);
    }

    @Override
    public void insertUserInfo(User user) {
        user.setAvatar(CONSTANTS.defaultAvatar);
        user.setRemainMoney(CONSTANTS.defaultMoney);
        userRepository.save(user);
    }

    @Override
    public UserDto getUserByUid(Long uid) {
        Optional<User> user = userRepository.getUserByUid(uid);
        return user.map(UserServiceImpl::mapToUserDto).orElse(null);
    }


    //Long uid, String slogan, String username, String avatar, Integer remainMoney
    @Override
    public UserDto updateUserInfo(UserDto user) {
        try {
            userRepository.PrivateSave(user.getUid(), user.getSlogan(), user.getUsername(), user.getAvatar(), user.getRemainMoney());
        }catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                return null;
            }
        }
        return user;
    }

    @Override
    public Boolean decreaseMoney(Long uid, Double spend) {
        return userRepository.decreaseMoney(uid, spend) == 1;
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

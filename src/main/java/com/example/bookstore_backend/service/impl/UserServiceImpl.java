package com.example.bookstore_backend.service.impl;

import com.example.bookstore_backend.Constants.Role;
import com.example.bookstore_backend.dao.OrderDao;
import com.example.bookstore_backend.dao.UserDao;
import com.example.bookstore_backend.dto.UserConsumingDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.dto.UserStateDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.repository.UserRepository;
import com.example.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserDao userDao;
    OrderDao orderDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, OrderDao orderDao){
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public UserDto getUserByUid(Long uid) {
        Optional<User> user = userDao.getUserByUid(uid);
        return user.map(UserServiceImpl::mapToUserDto).orElse(null);
    }


    //Long uid, String slogan, String username, String avatar, Integer remainMoney
    @Override
    public UserDto updateUserInfo(UserDto user) {
        try {
            userDao.PrivateSave(user);
        }catch(Exception e){
            if(e.getMessage().contains("Duplicate entry")){
                return null;
            }
        }
        return user;
    }

    @Override
    public Boolean banUserByUid(Long uid) {
        return userDao.updateUserStatusByUid(uid, Role.BANNED) == 1;
    }

    @Override
    public Boolean UnbanUserByUid(Long uid) {
        return userDao.updateUserStatusByUid(uid, Role.USER) == 1;
    }

    @Override
    public List<UserConsumingDto> getUserByRangingAndLimit(String startString, String endString, Integer limit) {
        LocalDateTime startTime, endTime;
        if(startString == null || startString.isEmpty()){
            startTime = LocalDateTime.of(1973, 1, 1, 0, 0, 0);
        }
        else startTime = LocalDateTime.parse(startString,DateTimeFormatter.ISO_DATE_TIME);


        if(startString == null || startString.isEmpty()){
            endTime = LocalDateTime.now();
        }
        else endTime = LocalDateTime.parse(startString,DateTimeFormatter.ISO_DATE_TIME);

        Page<UserConsumingDto> pages = orderDao.getUserByRangingAndConsuming(startTime, endTime, limit);
        return pages.getContent();
    }

    @Override
    public UserStateDto getUserStateByUsername(String username) {
        Optional<UserStateDto> opt = userDao.getUserStateByUsername(username);
        return opt.orElse(null);
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

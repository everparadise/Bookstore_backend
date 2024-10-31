package com.example.main.dao.impl;

import com.example.main.Constants.Role;
import com.example.main.dao.UserDao;
import com.example.main.dto.UserDto;
import com.example.main.dto.UserStateDto;
import com.example.main.model.User;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    UserRepository userRepository;
    @Autowired
    UserDaoImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByUid(Long uid) {
        return userRepository.getUserByUid(uid);
    }

    @Override
    public Integer PrivateSave(UserDto dto) {
        return userRepository.PrivateSave(dto.getUid(), dto.getSlogan(), dto.getUsername(), dto.getAvatar(), dto.getRemainMoney());
    }

    @Override
    public Integer decreaseMoney(Long uid, Integer cost) {
        return userRepository.decreaseMoney(uid, cost);
    }

    @Override
    public Integer updateUserStatusByUid(Long uid, Role role) {
        return userRepository.updateUserStatusByUid(uid, role);
    }

    @Override
    public Optional<UserStateDto> getUserStateByUsername(String username) {
        return userRepository.getUserStateByUsername(username);
    }
}

package com.example.main.service;

import com.example.main.dto.UserConsumingDto;
import com.example.main.dto.UserDto;
import com.example.main.dto.UserStateDto;

import java.util.List;

public interface UserService {
    public UserDto getUserByUid(Long uid);
    public UserDto updateUserInfo(UserDto user);
    public Boolean banUserByUid(Long uid);
    public Boolean UnbanUserByUid(Long uid);
    public List<UserConsumingDto> getUserByRangingAndLimit(String startString, String endString, Integer limit);
    public UserStateDto getUserStateByUsername(String username);
}

package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.dto.UserStateDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.service.UserService;
import com.example.bookstore_backend.service.impl.UserServiceImpl;
import com.example.bookstore_backend.util.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    UserService userServiceImpl;


    @Autowired
    public UserController(UserService userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("adminTest")
    public ResponseDto<Boolean> adminAuthTest(){
        return new ResponseDto<>(true, "authorization success", true);
    }
    @GetMapping
    public ResponseDto<UserDto> getUserByUid(){
        Long uid = UserProvider.getUserUid();
        UserDto user = userServiceImpl.getUserByUid(uid);
        if(user != null){
            return new ResponseDto<>(true, "Operation OK", user);
        }
        else return new ResponseDto<>(false, "user {%d} can't found".formatted(uid), null);
    }

    @GetMapping("{username}")
    public ResponseDto<UserStateDto> getUserByUsername(@PathVariable String username){
        UserStateDto dto = userServiceImpl.getUserStateByUsername(username);
        if(dto == null){
            return new ResponseDto<>(false, "no target user", null);
        }
        return new ResponseDto<>(true, "Operation OK", dto);
    }

    @PutMapping
    public ResponseDto<Boolean> updateUserInfo(@RequestBody UserDto user){
        user.setUid(UserProvider.getUserUid());
        if(userServiceImpl.updateUserInfo(user) != null )
            return new ResponseDto<>(true, "Operation OK", true);
        else return new ResponseDto<>(false, "Duplicate username", false);
    }

    @PutMapping("ban/{uid}")
    public ResponseDto<Boolean> banUser(@PathVariable Long uid){
        Boolean success = userServiceImpl.banUserByUid(uid);
        if(success){
            return new ResponseDto<>(true, "Operation OK", true);
        }
        else return new ResponseDto<>(false, "no target user", false);
    }

    @PutMapping("unban/{uid}")
    public ResponseDto<Boolean> unbanUser(@PathVariable Long uid){
        Boolean success = userServiceImpl.UnbanUserByUid(uid);
        if(success){
            return new ResponseDto<>(true, "Operation OK", true);
        }
        else return new ResponseDto<>(false, "no target user", false);
    }
}

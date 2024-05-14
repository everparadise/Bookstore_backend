package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.Constants.CONSTANTS;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.model.User;
import com.example.bookstore_backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/getUser/{uid}")
    public ResponseDto<UserDto> getUserByUid(@PathVariable Integer uid){
        UserDto user = userServiceImpl.getUserByUid(uid);
        if(user != null){
            return new ResponseDto<>(true, "Operation OK", user);
        }
        else return new ResponseDto<>(false, "user {%d} can't found".formatted(uid), null);
    }

    @PostMapping("/loginRequest")
    public ResponseDto<UserDto> loginRequest(@RequestBody User user){

        UserDto dto = userServiceImpl.loginRequest(user.getUsername(), user.getPassword());
        if(dto != null){
            return new ResponseDto<>(true, "login Success", dto);
        }
        else return new ResponseDto<>(false, "login Failed", null);
    }

    @PutMapping("/userInfo")
    public ResponseDto<Boolean> updateUserInfo(@RequestBody UserDto user){

        if(userServiceImpl.updateUserInfo(user) != null )
            return new ResponseDto<>(true, "Operation OK", true);
        else return new ResponseDto<>(false, "Duplicate username", false);
    }

    @PostMapping("addUser")
    public ResponseDto<UserDto> addUserInfo(@RequestBody User user){
        userServiceImpl.insertUserInfo(user);
        return new ResponseDto<>(true, "Operation OK", null);
    }

}

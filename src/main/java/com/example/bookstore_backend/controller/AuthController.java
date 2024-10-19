package com.example.bookstore_backend.controller;

import com.example.bookstore_backend.dto.AuthRequest;
import com.example.bookstore_backend.model.AuthUser;
import com.example.bookstore_backend.service.TimerService;
import com.example.bookstore_backend.service.impl.AuthService;
import com.example.bookstore_backend.dto.ResponseDto;
import com.example.bookstore_backend.dto.UserDto;
import com.example.bookstore_backend.util.JWTService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;


import javax.swing.*;
import java.util.Objects;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
//登录验证
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostMapping("/login")
    public ResponseDto<String> authenticate(
            @RequestBody AuthRequest request
            ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final AuthUser user = authService.loadUserByUsername(request.getUsername());
        TimerService timerService = webApplicationContext.getBean(TimerService.class);
        if(user != null){
            String status = user.getStatus();
            timerService.startTimer();
            return new ResponseDto<>(true, status, jwtService.generateToken(user));
        }
        return new ResponseDto<>(false, "username or password wrong", "wrong");
    }

    @PostMapping("/register")
    public ResponseDto<String> register(
            @RequestBody AuthRequest request
    ){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        try{
            UserDetails userDetails = authService.registerNewUser(request);
            return new ResponseDto<>(true, "register succuess", jwtService.generateToken(userDetails));
        }catch(Exception e){
            if(Objects.equals(e.getMessage(), "email format wrong")){
                return new ResponseDto<>(false, "email format wrong", "email");
            }
            else if(Objects.equals(e.getMessage(), "username duplicate")){
                return new ResponseDto<>(false, "username duplicate", "username");
            }

        }

    return new ResponseDto<>(false, "should never reach here", "wrong");
    }

    @GetMapping("/logout")
    public ResponseDto<Long> logout(){
        TimerService timerService = webApplicationContext.getBean(TimerService.class);
        try{
            Long duration = timerService.endTimer();
            return new ResponseDto<>(true, "timer end success", duration);
        }catch(RuntimeException runtimeException){
            return new ResponseDto<>(false, "timer init error", null);
        }
    }
}

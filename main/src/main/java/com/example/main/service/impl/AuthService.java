package com.example.main.service.impl;

import com.example.main.dto.AuthRequest;
import com.example.main.model.AuthUser;
import com.example.main.Constants.Role;
import com.example.main.dao.AuthDao;
import com.example.main.model.User;
import com.example.main.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;

//实现UserDetailsService 得到UserDetail(user) 用于后续过滤 提取
@Service
public class AuthService implements UserDetailsService {
    UserRepository userRepository;

    private static Validator validator;

    AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
        if(validator == null){
            setUpValidator();
        }
    }
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AuthDao> res =  userRepository.getAuthUserByUsername(username);
        if(res.isPresent()){
            return new AuthUser(res.get());
        }
        else throw new UsernameNotFoundException("No User was found");
    }

    public UserDetails registerNewUser(AuthRequest auth){
        User newUser = User.builder()
                .avatar("https://ui-avatars.com/api/?name=NewUser")
                .remainMoney(1000000)
                .password(auth.getPassword())
                .username(auth.getUsername())
                .role(Role.USER)
                .email(auth.getEmail())
                .build();

        Set<ConstraintViolation<User>> constraintViolations =validator.validateProperty(newUser, "email");

        if(constraintViolations.size() == 1){
            throw new IllegalArgumentException("email format wrong");
        }
        try{
            User res = userRepository.save(newUser);
            return new AuthUser(res);
        }catch(Exception e){
            throw new IllegalArgumentException("username duplicate");
        }

    }

}

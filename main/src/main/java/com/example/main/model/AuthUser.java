package com.example.main.model;

import com.example.main.dao.AuthDao;
import com.example.main.util.PrivateGrantedAuthority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


// 实现 UserDetails 接口，为在过滤链中操作User对象而封装UserDao
@Getter
@Setter
@RequiredArgsConstructor
public class AuthUser implements UserDetails {
    private final AuthDao user;

    public AuthUser(User user){
        this.user = new AuthDao(user.getUsername(), user.getPassword(), user.getUid(), user.getRole());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new PrivateGrantedAuthority("ROLE_"+user.getRole().toString()));
    }

    public String getStatus(){
        return user.getRole().toString();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

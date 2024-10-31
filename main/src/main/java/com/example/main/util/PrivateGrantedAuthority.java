package com.example.main.util;

import org.springframework.security.core.GrantedAuthority;

public class PrivateGrantedAuthority implements GrantedAuthority {
    private final String role;
    public PrivateGrantedAuthority(String role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}

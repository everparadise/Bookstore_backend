package com.example.main.dao;

import com.example.main.Constants.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthDao {
    public String username;
    public String password;
    public Long uid;
    @Enumerated(EnumType.STRING)
    public Role role;

    public AuthDao(){
        this.role = Role.USER;
    }
}

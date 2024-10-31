package com.example.main.dto;

import com.example.main.Constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStateDto {
    Long uid;
    String username;
    String avatar;
    Role role;
}

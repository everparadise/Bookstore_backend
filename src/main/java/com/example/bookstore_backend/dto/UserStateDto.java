package com.example.bookstore_backend.dto;

import com.example.bookstore_backend.Constants.Role;
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

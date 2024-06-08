package com.example.bookstore_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserConsumingDto {
    Long uid;
    String username;
    Long consuming;
}

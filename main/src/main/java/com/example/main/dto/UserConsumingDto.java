package com.example.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserConsumingDto {
    Long uid;
    String username;
    Long consuming;
}

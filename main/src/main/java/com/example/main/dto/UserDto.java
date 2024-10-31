package com.example.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long uid;
    private String username;
    private Integer remainMoney;
    private String slogan;
    private String avatar;

}

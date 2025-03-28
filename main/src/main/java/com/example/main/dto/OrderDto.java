package com.example.main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//used for receiving data from frontend
public class OrderDto{
    private Long uid;
    private String address;
    private String telephone;
    private String receiver;
    private List<NewOrderItemDto> items;
    private String token;
}

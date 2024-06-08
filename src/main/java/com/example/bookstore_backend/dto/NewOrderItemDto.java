package com.example.bookstore_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderItemDto {
    private Integer numbers;
    private Long bid;
    private Long cid;
}

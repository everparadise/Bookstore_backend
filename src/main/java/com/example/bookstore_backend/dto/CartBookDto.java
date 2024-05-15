package com.example.bookstore_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBookDto {
    private Long cid;
    private Long bid;
    private Long uid;
    private String name;
    private Double price;
    private String pic;
    private String comment;
    private Integer number;
    private Boolean selected;
}

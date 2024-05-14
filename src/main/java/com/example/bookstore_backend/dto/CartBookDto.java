package com.example.bookstore_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBookDto {
    private Integer cid;
    private Integer bid;
    private Integer uid;
    private String name;
    private Double price;
    private String pic;
    private String comment;
    private Integer number;
    private Boolean selected;
}

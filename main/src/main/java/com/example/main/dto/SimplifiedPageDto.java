package com.example.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedPageDto<T> {
    private List<T> content;
    private Integer totalPages;
    private Integer totalElements;

}

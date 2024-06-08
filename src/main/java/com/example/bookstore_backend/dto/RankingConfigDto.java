package com.example.bookstore_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingConfigDto {
    private String bookStartTime;
    private String bookEndTime;

    private String userStartTime;
    private String userEndTime;
}

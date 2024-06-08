package com.example.bookstore_backend.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data

@Component
@ConfigurationProperties(prefix = "ranking.config")
public class RankingConfig {
    private LocalDateTime bookStartTime;
    private LocalDateTime bookEndTime;

    private LocalDateTime userStartTime;
    private LocalDateTime userEndTime;
}

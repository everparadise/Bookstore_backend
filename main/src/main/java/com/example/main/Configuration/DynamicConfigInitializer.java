package com.example.main.Configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DynamicConfigInitializer {
    @Autowired
    private RankingConfig rankingConfig;

    @PostConstruct
    public void init(){
        rankingConfig.setBookStartTime(LocalDateTime.now());
        rankingConfig.setBookEndTime(LocalDateTime.of(1973, 1, 1, 0 , 0, 0));

        rankingConfig.setUserStartTime(LocalDateTime.of(1973, 1, 1, 0 , 0, 0));
        rankingConfig.setUserEndTime(LocalDateTime.now());
    }
}

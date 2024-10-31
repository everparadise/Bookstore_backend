package com.example.main.service.impl;

import com.example.main.service.TimerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class TimerServiceImpl implements TimerService {
    private long startTime;
    private boolean running;

    public TimerServiceImpl() {
        running = false;
    }

    @Override
    public long endTimer() {
        if(running){
            long endTime = System.currentTimeMillis();
            running = false;
            return endTime - startTime;
        }
        else throw new RuntimeException("timer not init");
    }

    @Override
    public void startTimer() {
        running = true;
        startTime = System.currentTimeMillis();
    }
}

package com.example.calculatefunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class CalculateFunctionApplication {
    @Bean
    public Function<Flux<Integer[]>, Flux<Integer>> calculatePrice() {
        return flux -> flux.map(value -> value[0] * value[1]);
    }

    public static void main(String[] args) {
        SpringApplication.run(CalculateFunctionApplication.class, args);
    }

}

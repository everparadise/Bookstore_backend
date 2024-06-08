package com.example.bookstore_backend;

import com.example.bookstore_backend.Configuration.RankingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RankingConfig.class)
public class BookstoreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackendApplication.class, args);
	}

}

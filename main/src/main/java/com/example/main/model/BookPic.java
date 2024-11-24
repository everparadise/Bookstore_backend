package com.example.main.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor

@Document(collection = "bookpic")
public class BookPic {
    @Id
    private Long bid;
    private String picBase64;
}

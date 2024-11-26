package com.example.main.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor

@Document(collection = "bookpic")
public class BookPic {
    @Id
    private Long bid;
    @Indexed
    private String filename;
    private String picBase64;
}

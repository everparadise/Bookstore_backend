package com.example.main.repository;

import com.example.main.model.BookPic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookPicRepository extends MongoRepository<BookPic, Long>{
}

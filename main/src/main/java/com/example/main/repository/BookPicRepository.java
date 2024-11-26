package com.example.main.repository;

import com.example.main.model.BookPic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface BookPicRepository extends MongoRepository<BookPic, Long>{
    public Boolean deleteBookPicByBid(Long bid);

//    @Query("{'filename': ?0}")
//    @Update("{ '$set':{'picBase64': ?1}}")
//    public BookPic updateBookPicByFilename(String Filename, String picBase64);
//
//    @Query("{'filename': ?0}")
//    @Update("{ '$set':{'bid': ?1}}")
//    public void setBidForBookPic(String filename, Long bid);

    public Optional<BookPic> getBookPicByFilename(String filename);
}

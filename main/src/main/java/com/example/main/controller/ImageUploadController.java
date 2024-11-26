package com.example.main.controller;

import com.example.main.dto.ResponseDto;
import com.example.main.Constants.CONSTANTS;
import com.example.main.model.BookPic;
import com.example.main.repository.BookPicRepository;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("v1/image")
public class ImageUploadController {
    @Autowired
    private BookPicRepository bookPicRepository;

    @PostMapping("/upload/{bid}")
    public ResponseDto<?> uploadImage(@RequestParam(value = "image") MultipartFile file, @PathVariable Long bid){

        //@RequestParam(value = "image") MultipartFile file
        if(file.isEmpty()){
            return new ResponseDto<>(false, "please select a file", false);
        }
        try{
            String uniqueID = UUID.randomUUID().toString();
            String filename = uniqueID + "_" + file.getOriginalFilename();

            byte[] fileBytes = file.getBytes();;
            String base64Data = Base64.getEncoder().encodeToString(fileBytes);

            BookPic bookPic = new BookPic(bid,filename, base64Data);
            bookPicRepository.save(bookPic);

            String fileUrl = CONSTANTS.serverUrlPre + "images/" + filename;
            return new ResponseDto<>(true, "Operation OK", fileUrl);
        } catch (IOException e) {
            return new ResponseDto<>(false, "Error upload file", false);
        }
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String filename){
        System.out.println("getImage - filename: " + filename);
        try{
           Optional<BookPic> bookPic = bookPicRepository.getBookPicByFilename(filename);
            if(bookPic.isPresent()) {
                String base64Data = bookPic.get().getPicBase64();
                byte[] imageBytes = Base64.getDecoder().decode(base64Data);
                String mimeType = Files.probeContentType(Paths.get(filename));
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(mimeType));
                System.out.println("find target image:" + filename);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

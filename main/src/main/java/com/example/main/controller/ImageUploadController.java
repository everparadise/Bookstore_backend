package com.example.main.controller;

import com.example.main.dto.ResponseDto;
import com.example.main.Constants.CONSTANTS;
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
import java.util.UUID;

@RestController
@RequestMapping("v1/image")
public class ImageUploadController {
    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseDto<?> uploadImage(@RequestParam(value = "image") MultipartFile file){

        //@RequestParam(value = "image") MultipartFile file
        if(file.isEmpty()){
            return new ResponseDto<>(false, "please select a file", false);
        }
        try{
            String uniqueID = UUID.randomUUID().toString();
            String filename = uniqueID + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(uploadPath + File.separator + filename));
            String fileUrl = CONSTANTS.serverUrlPre + "images/" + filename;
            return new ResponseDto<>(true, "Operation OK", fileUrl);
        } catch (IOException e) {
            return new ResponseDto<>(false, "Error upload file", false);
        }
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String filename){
        try{
            File file = new File(uploadPath + File.separator + filename);
            if(file.exists()){
                byte[] imageBytes = Files.readAllBytes(file.toPath());
                String mimeType = Files.probeContentType(file.toPath());
                if(mimeType == null){
                    mimeType = "application/octet-stream";
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(mimeType));
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

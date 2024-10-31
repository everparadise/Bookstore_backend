package com.example.author.controller;

import com.example.author.dto.ResponseDto;
import com.example.author.service.NameAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/author")
public class NameAuthorController {

    @Autowired
    NameAuthorService nameAuthorService;

    @GetMapping("{name}")
    public ResponseDto<List<String>> getAuthorByName(@PathVariable String name){
        System.out.println(name);
        try{
            List<String> optString = nameAuthorService.getAuthorByName(name);
            return new ResponseDto<>(true, "Success", optString);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseDto<>(false, "Server Internal Error", null);
        }
    }
}

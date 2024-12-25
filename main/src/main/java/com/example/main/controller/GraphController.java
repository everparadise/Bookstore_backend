package com.example.main.controller;

import com.example.main.model.Book;
import com.example.main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphController {
    @Autowired
    BookService bookServiceImpl;

    @QueryMapping
    public Book getBookByName(@Argument String name){
        System.out.println("GraphQL query for book name: " + name);
        return bookServiceImpl.getBookByName(name);
    }


}

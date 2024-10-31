package com.example.author.service.impl;

import com.example.author.dao.BookDao;
import com.example.author.service.NameAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NameAuthorServiceImpl implements NameAuthorService {
    @Autowired
    private BookDao bookDao;
    @Override
    public List<String> getAuthorByName(String name) {
        return bookDao.getBookAuthorByName(name);
    }
}

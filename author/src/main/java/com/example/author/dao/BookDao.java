package com.example.author.dao;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<String> getBookAuthorByName(String name);
}

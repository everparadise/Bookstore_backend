package com.example.main.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Generic method to deserialize JSON to a specific object type
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    // Generic method to serialize an object to JSON
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}

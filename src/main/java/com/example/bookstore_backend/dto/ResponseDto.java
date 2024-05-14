package com.example.bookstore_backend.dto;

public record ResponseDto<T>(boolean valid, String message, T resource) {

}

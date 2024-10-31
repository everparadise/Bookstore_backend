package com.example.author.dto;

public record ResponseDto<T>(boolean valid, String message, T resource) {
}
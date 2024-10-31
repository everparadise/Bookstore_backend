package com.example.main.dto;

public record ResponseDto<T>(boolean valid, String message, T resource) {

}

package com.spring_app.demo.dtos;

public record ErrorResponseDTO(int statusCode, String cause, String message) {



}

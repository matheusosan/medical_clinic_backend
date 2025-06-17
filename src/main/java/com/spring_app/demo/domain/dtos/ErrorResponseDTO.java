package com.spring_app.demo.domain.dtos;

public record ErrorResponseDTO(int statusCode, String cause, String message) {



}

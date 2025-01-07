package com.spring_app.demo.dtos.Client;

import com.spring_app.demo.entities.Client;

import java.time.LocalDate;

public record ClientRequestDTO(String name,
        String cpf,
        String email,
        String password,
        String phoneNumber,
        Client.UserRoles role,
        LocalDate birthDate) {
}

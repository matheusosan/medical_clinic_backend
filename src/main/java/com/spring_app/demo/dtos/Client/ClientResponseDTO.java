package com.spring_app.demo.dtos.Client;

import com.spring_app.demo.entities.UserRoles;

import java.time.LocalDate;

public record ClientResponseDTO( Long id, String name, String email, String phoneNumber, String cpf, LocalDate birthDate, UserRoles role) {

}

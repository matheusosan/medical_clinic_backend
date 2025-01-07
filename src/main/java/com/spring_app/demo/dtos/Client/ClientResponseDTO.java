package com.spring_app.demo.dtos.Client;

import com.spring_app.demo.entities.Client;

import java.time.LocalDate;

public record ClientResponseDTO( Long id, String name, String email, String phoneNumber, String cpf, LocalDate birthDate, Client.UserRoles role) {

}

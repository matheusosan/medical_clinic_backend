package com.spring_app.demo.domain.dtos.Client;

import com.spring_app.demo.domain.entities.Client;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String cpf;
    private LocalDate birthDate;
    private Client.UserRoles role;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Client.UserRoles getRole() {
        return role;
    }

    public void setRole(Client.UserRoles role) {
        this.role = role;
    }

    public static ClientResponseDTO fromEntity(Client client) {
        if (client == null) {
            return null;
        }

        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setCpf(client.getCpf());
        dto.setBirthDate(client.getBirthDate());
        dto.setRole(client.getRole());

        return dto;
    }
}


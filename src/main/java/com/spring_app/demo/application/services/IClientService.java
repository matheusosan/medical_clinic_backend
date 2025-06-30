package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.dtos.Client.ClientRequestDTO;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    Client createClient(ClientRequestDTO dto);
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO findClientByCPF(String cpf);
    Client findById(UUID id);
    Client getByEmail(String email);
}

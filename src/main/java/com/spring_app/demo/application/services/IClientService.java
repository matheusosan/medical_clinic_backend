package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.dtos.Client.ClientRequestDTO;

import java.util.List;

public interface IClientService {
    Client createClient(ClientRequestDTO dto);
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO findClientByCPF(String cpf);
    Client findById(long id);
    Client getByEmail(String email);
}

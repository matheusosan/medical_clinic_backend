package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Client.ClientRequestDTO;
import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientAlreadyExistsException;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.infra.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client createClient(ClientRequestDTO dto) {

        Optional<Client> clientExists = clientRepository.findByCpf(dto.getCpf());

        if(clientExists.isPresent()) {
            throw new ClientAlreadyExistsException("Usuário já cadastrado no sistema!");
        }

        Client client = Client.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .cpf(dto.getCpf())
                .birthDate(dto.getBirthDate())
                .phoneNumber(dto.getPhoneNumber())
                .role(Client.UserRoles.USER).build();

        return clientRepository.save(client);
    }

    public List<ClientResponseDTO> getAllClients() {
        var clientsEntity = clientRepository.findAll();

        return clientsEntity.stream()
                .map(ClientResponseDTO::fromEntity)
                .collect(Collectors.toList());
    };

    public ClientResponseDTO findClientByCPF(String cpf) {
        var clientEntity = clientRepository.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException("Usuário não encontrado com o CPF fornecido!"));

        return ClientResponseDTO.fromEntity(clientEntity);
    };

    public Client findById(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado."));
    }

    public Client getByEmail(String email) {
        return clientRepository.getByEmail(email).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado."));
    }

}

package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Client.ClientRequestDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientAlreadyExistsException;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.infra.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    };

    public Client findClientByCPF(String cpf) {
        return clientRepository.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException("Usuário não encontrado com o CPF fornecido!"));
    };

    public Client findById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado."));
    }

    public Client getByEmail(String email) {
        return clientRepository.getByEmail(email).orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado."));
    }

}

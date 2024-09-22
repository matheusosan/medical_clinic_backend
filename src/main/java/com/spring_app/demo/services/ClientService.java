package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Client.ClientRequestDTO;
import com.spring_app.demo.entities.Client;
import com.spring_app.demo.entities.UserRoles;
import com.spring_app.demo.exceptions.ClientExceptions.ClientAlreadyExistsException;
import com.spring_app.demo.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client createClient(ClientRequestDTO dto) {

        Optional<Client> clientExists = clientRepository.findByCpf(dto.cpf());

        if(clientExists.isPresent()) {
            throw new ClientAlreadyExistsException("Usuário já cadastrado no sistema!");
        }

        Client client = Client.builder()
                .email(dto.email())
                .name(dto.name())
                .password(passwordEncoder.encode(dto.password()))
                .cpf(dto.cpf())
                .birthDate(dto.birthDate())
                .phoneNumber(dto.phoneNumber())
                .role(UserRoles.USER).build();

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

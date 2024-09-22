package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Client.ClientRequestDTO;
import com.spring_app.demo.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.dtos.SuccessResponseDTO;
import com.spring_app.demo.entities.Client;
import com.spring_app.demo.security.TokenService;
import com.spring_app.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    ResponseEntity<SuccessResponseDTO> createClient(@RequestBody ClientRequestDTO dto ) {
        Client newClient = clientService.createClient(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient).toUri();
        SuccessResponseDTO response = new SuccessResponseDTO(201, "Paciente cadastrado com sucesso!");

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping()
    List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/profile")
    ResponseEntity<ClientResponseDTO> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        String userEmail = tokenService.validateToken(token);
        Client client = clientService.getByEmail(userEmail);

        ClientResponseDTO response = new ClientResponseDTO(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getCpf(), client.getBirthDate(), client.getRole());

        return ResponseEntity.ok().body(response);
    };

    @GetMapping("/cpf/{cpf}")
    ResponseEntity<ClientResponseDTO> findByCPF(@PathVariable String cpf) {
        Client client = clientService.findClientByCPF(cpf);

        ClientResponseDTO response = new ClientResponseDTO(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getCpf(), client.getBirthDate(), client.getRole());

        return ResponseEntity.ok(response);
    }


}

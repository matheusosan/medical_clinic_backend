package com.spring_app.demo.controllers;

import com.spring_app.demo.application.services.IClientService;
import com.spring_app.demo.domain.dtos.Client.ClientRequestDTO;
import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.dtos.ErrorResponseDTO;
import com.spring_app.demo.domain.dtos.SuccessResponseDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.security.ITokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
@Tag(name = "Client", description = "Manipula operações relacionadas a clientes.")
@Validated
public class ClientController {
    private final IClientService clientService;
    private final ITokenService tokenService;

    public ClientController(IClientService clientService, ITokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Cadastra um cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
            @ApiResponse(
                    responseCode = "409",
                    description = "Erro: Cliente já cadastrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
    })
    @PostMapping()
    ResponseEntity<SuccessResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO dto ) {
        Client newClient = clientService.createClient(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient).toUri();
        SuccessResponseDTO response = new SuccessResponseDTO(201, "Paciente cadastrado com sucesso!");

        return ResponseEntity.created(location).body(response);
    }


    @Operation(summary = "Busca todos clientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca todos os clientes.")
    })
    @GetMapping()
    List<Client> getAllClients() {
        return clientService.getAllClients();
    }


    @Operation(summary = "Busca os dados do perfil do cliente", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do perfil do cliente buscados com sucesso.")
    })
    @GetMapping("/profile")
    ResponseEntity<ClientResponseDTO> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        String userEmail = tokenService.validateToken(token);
        Client client = clientService.getByEmail(userEmail);

        ClientResponseDTO response = new ClientResponseDTO(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getCpf(), client.getBirthDate(), client.getRole());

        return ResponseEntity.ok().body(response);
    };

    @Operation(summary = "Busca um cliente por CPF", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Erro: Cliente não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            ),
    })
    @GetMapping("/cpf/{cpf}")
    ResponseEntity<ClientResponseDTO> findByCPF(@PathVariable String cpf) {
        Client client = clientService.findClientByCPF(cpf);

        ClientResponseDTO response = new ClientResponseDTO(client.getId(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getCpf(), client.getBirthDate(), client.getRole());

        return ResponseEntity.ok(response);
    }


}

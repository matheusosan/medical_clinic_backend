package com.spring_app.demo.controllers;

import com.spring_app.demo.application.services.IAuthenticationService;
import com.spring_app.demo.domain.dtos.Authentication.AuthenticationDTO;

import com.spring_app.demo.domain.dtos.Authentication.LoginResponseDTO;
import com.spring_app.demo.domain.dtos.ErrorResponseDTO;
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

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
@Tag(name = "Authentication", description = "Manipula operações relacionadas a autenticação.")
@Validated
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Autentica cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente autenticado com sucesso.",  content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDTO.class)
            )),
            @ApiResponse(
                    responseCode = "401",
                    description = "Erro: Credenciais inválidas.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO dto) {
        String token = this.authenticationService.login(dto);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
};



package com.spring_app.demo.controllers;

import com.spring_app.demo.application.services.ISpecialityService;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.domain.dtos.SuccessResponseDTO;
import com.spring_app.demo.domain.entities.Speciality;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/service")
@CrossOrigin(value = "*")
@Tag(name = "Service", description = "Manipula operações relacionadas a services.")
@Validated
public class SpecialityController {
    private final ISpecialityService specialityService;

    public SpecialityController(ISpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Operation(summary = "Busca todos serviços", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços buscados com sucesso."),

    })
    @GetMapping()
    List<Speciality> getAllServices() {
        return specialityService.getAllServices();
    }


    @Operation(summary = "Cadastra um cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),

    })
    @PostMapping()
    ResponseEntity<SuccessResponseDTO> createService(@Valid @RequestBody ServiceRequestDTO dto) {
        Speciality newSpeciality = specialityService.createService(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSpeciality).toUri();

        return ResponseEntity.created(location).body(new SuccessResponseDTO(201, "Serviço cadastrado com sucesso!"));
    }
}

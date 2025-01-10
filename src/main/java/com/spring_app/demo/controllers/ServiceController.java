package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.dtos.SuccessResponseDTO;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.services.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ServiceController {

    @Autowired
    private ServiceService serviceService;


    @Operation(summary = "Busca todos serviços", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços buscados com sucesso."),

    })
    @GetMapping()
    List<Service> getAllServices() {
        return serviceService.getAllServices();
    }


    @Operation(summary = "Cadastra um cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),

    })
    @PostMapping()
    ResponseEntity<SuccessResponseDTO> createService(@Valid @RequestBody ServiceRequestDTO dto) {
        Service newService = serviceService.createService(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newService).toUri();

        return ResponseEntity.created(location).body(new SuccessResponseDTO(201, "Serviço cadastrado com sucesso!"));
    }
}

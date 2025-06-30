package com.spring_app.demo.controllers;

import com.spring_app.demo.application.services.ISpecialityService;
import com.spring_app.demo.domain.dtos.ApiResponseDto;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.domain.dtos.Service.ServiceResponseDTO;
import com.spring_app.demo.domain.dtos.Service.ServiceResponseDTOSwagger;
import com.spring_app.demo.domain.dtos.SuccessResponseDTO;
import com.spring_app.demo.domain.entities.Speciality;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
@Tag(name = "Service", description = "Manipula operações relacionadas a services.")
@Validated
public class SpecialityController {
    private final ISpecialityService specialityService;

    public SpecialityController(ISpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Operation(summary = "Busca todos serviços", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviços buscados com sucesso.",  content = @Content(schema = @Schema(implementation = ServiceResponseDTOSwagger.class))),
    })
    @GetMapping()
    ResponseEntity<ApiResponseDto<List<ServiceResponseDTO>>> getAllServices() {
        var specialities = specialityService.getAllServices();
        return ResponseEntity.ok(new ApiResponseDto<>(specialities, specialities.isEmpty() ? "Não foram encontrados serviços." : "Serviços buscados com sucessso!", HttpStatus.OK.value()));
    }


    @Operation(summary = "Cadastra um cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.",  content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
    })
    @PostMapping()
    ResponseEntity<ApiResponseDto> createService(@Valid @RequestBody ServiceRequestDTO dto) {
        Speciality newSpeciality = specialityService.createService(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSpeciality).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto<>(null, "Serviço cadastrado com sucesso!", HttpStatus.CREATED.value()));
    }
}

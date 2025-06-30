package com.spring_app.demo.controllers;

import com.spring_app.demo.application.services.IAppointmentService;
import com.spring_app.demo.domain.dtos.ApiResponseDto;
import com.spring_app.demo.domain.dtos.Appointment.*;
import com.spring_app.demo.domain.dtos.ErrorResponseDTO;
import com.spring_app.demo.domain.dtos.SuccessResponseDTO;
import com.spring_app.demo.domain.entities.Appointment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
@Tag(name = "Appointment", description = "Manipula operações relacionadas a agendamentos")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Busca todos agendamentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso.", content = @Content(schema = @Schema(implementation = AppointmentResponseDTOSwagger.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: Agendamento não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping()
    ResponseEntity<ApiResponseDto<List<AppointmentResponseDTO>>> getAllAppointments() {
        var appointments = appointmentService.getAllAppointments();

        return ResponseEntity.ok(new ApiResponseDto<>(appointments, appointments.isEmpty() ? "Não foram encontrados agendamentos" : "Agendamentos retornados com sucesso!", HttpStatus.OK.value()));
    }


    @Operation(summary = "Busca agendamentos dado uma data e o ID do serviço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso", content = @Content(schema = @Schema(implementation = AppointmentByDateAndServiceSwagger.class)))
    })
    @GetMapping("/date")
    public ResponseEntity<ApiResponseDto<List<AppointmentByDateAndService>>> getAppointmentsByDateAndServiceId(@RequestParam("date") String date, @RequestParam("specialityId") UUID specialityId) {
        LocalDate localDate = LocalDate.parse(date);
        var appointments = appointmentService.findAllByDateAndServiceId(localDate, specialityId);
        return ResponseEntity.ok(new ApiResponseDto<>(appointments,  appointments.isEmpty() ? "Não foarm encontrados agendamentos" : "Agendamentos retornados com sucesso!", HttpStatus.OK.value()));
    }


    @Operation(summary = "Busca todos agendamentos com base no ID de cliente", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso.", content = @Content(schema = @Schema(implementation = AppointmentResponseDTOSwagger.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: Agendamento não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/client/{id}")
    public ResponseEntity<ApiResponseDto<List<AppointmentResponseDTO>>> findAllAppointmentsByUserId( @PathVariable UUID id, @RequestParam(required = false, defaultValue = "newest") String sortBy) {
        var appointments = appointmentService.findAllAppointmentsByUserId(id, sortBy);
        return ResponseEntity.ok(new ApiResponseDto<>(appointments, appointments.isEmpty() ? "Não foram encontrados agendamentos" : "Agendamentos retornados com sucesso!", HttpStatus.OK.value()));
    }


    @Operation(summary = "Cancela um agendamento", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso.", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: Agendamento não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PatchMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseDto> cancelAppointment(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(new ApiResponseDto(null, "Agendamento cancelado com sucesso!", HttpStatus.OK.value()));
    }


    @Operation(summary = "Cria um agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso.", content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: A data está fora do período permitido.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponseDto> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        Appointment newAppointment = appointmentService.createAppointment(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAppointment).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto<>(null, "Consulta agendada com sucesso!", HttpStatus.OK.value()));

    }
}

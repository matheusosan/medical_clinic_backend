package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.dtos.ErrorResponseDTO;
import com.spring_app.demo.dtos.SuccessResponseDTO;
import com.spring_app.demo.entities.Appointment;
import com.spring_app.demo.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Tag(name = "Appointment", description = "Manipula operações relacionadas a agendamentos")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Busca todos agendamentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso")
    })
    @GetMapping()
    List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }


    @Operation(summary = "Busca agendamentos dado uma data e o ID do serviço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso")
    })
    @GetMapping("/date")
    public List<AppointmentRequestDTO> getAppointmentsByDateAndServiceId(@RequestParam("date") String date, @RequestParam("serviceId") Long serviceId) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentService.findAllByDateAndServiceId(localDate, serviceId);
    }


    @Operation(summary = "Busca todos agendamentos com base no ID de cliente", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos buscados com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: Agendamento não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @GetMapping("/client/{id}")
    public List<Appointment> findAllAppointmentsByUserId( @PathVariable Long id, @RequestParam(required = false, defaultValue = "newest") String sortBy) {
        return appointmentService.findAllAppointmentsByUserId(id, sortBy);
    }


    @Operation(summary = "Cancela um agendamento", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: Agendamento não encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PatchMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponseDTO> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(new SuccessResponseDTO(200, "Consulta cancelada!"));
    }


    @Operation(summary = "Cria um agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro: A data está fora do período permitido.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        Appointment newAppointment = appointmentService.createAppointment(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAppointment).toUri();

        return ResponseEntity.created(location).body(new SuccessResponseDTO(201, "Consulta agendada com sucesso!"));

    }
}

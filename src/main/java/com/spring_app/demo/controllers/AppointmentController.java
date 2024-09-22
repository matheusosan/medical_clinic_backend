package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.entities.Appointment;
import com.spring_app.demo.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping()
    List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/date")
    public List<AppointmentRequestDTO> getAppointmentsByDateAndServiceId(@RequestParam("date") String date, @RequestParam("serviceId") Long serviceId) {
        LocalDate localDate = LocalDate.parse(date);
        return appointmentService.findAllByDateAndServiceId(localDate, serviceId);
    }

    @GetMapping("/client/{id}")
    public List<Appointment> findAllAppointmentsByUserId(@PathVariable Long id, @RequestParam(required = false, defaultValue = "newest") String sortBy) {
        return appointmentService.findAllAppointmentsByUserId(id, sortBy);
    }

    @PatchMapping("/cancel/{id}")
    ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Consulta cancelada com sucesso!");
    }

    @PostMapping()
    ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        Appointment newAppointment = appointmentService.createAppointment(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAppointment).toUri();

        return ResponseEntity.created(location).body(new AppointmentResponseDTO("Consulta agendada com sucesso!", "201"));

    }
}

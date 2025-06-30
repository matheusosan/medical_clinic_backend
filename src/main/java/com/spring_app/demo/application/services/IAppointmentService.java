package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Appointment.AppointmentByDateAndService;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.domain.entities.Appointment;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService {
    List<AppointmentResponseDTO> getAllAppointments();
    List<AppointmentByDateAndService> findAllByDateAndServiceId(LocalDate date, UUID serviceId);
    List<AppointmentResponseDTO> findAllAppointmentsByUserId(UUID id, String sortBy);
    void cancelAppointment(UUID id);
    Appointment createAppointment(AppointmentRequestDTO dto);
}

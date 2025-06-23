package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.domain.entities.Appointment;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    List<AppointmentResponseDTO> getAllAppointments();
    List<AppointmentRequestDTO> findAllByDateAndServiceId(LocalDate date, Long serviceId);
    List<Appointment> findAllAppointmentsByUserId(Long id, String sortBy);
    void cancelAppointment(Long id);
    Appointment createAppointment(AppointmentRequestDTO dto);
}

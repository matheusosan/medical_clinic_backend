package com.spring_app.demo.domain.dtos.Appointment;

import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.entities.Appointment;
import com.spring_app.demo.domain.entities.Speciality;

import java.time.Instant;

public class AppointmentResponseDTO {
    Long id;
    Instant dataAgendada;
    Speciality speciality;
    ClientResponseDTO client;
    String cancellationReason;
    Appointment.AppointmentStatus status;

    public AppointmentResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Instant dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Appointment.AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(Appointment.AppointmentStatus status) {
        this.status = status;
    }
}

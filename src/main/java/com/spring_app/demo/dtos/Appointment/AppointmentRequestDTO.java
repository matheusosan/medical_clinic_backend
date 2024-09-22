package com.spring_app.demo.dtos.Appointment;

import com.spring_app.demo.entities.Appointment;

import java.time.Instant;

public class AppointmentRequestDTO {

    Instant dataAgendada;
    Long serviceId;
    Long clientId;
    Appointment.AppointmentStatus status;

    public AppointmentRequestDTO(Instant dataAgendada, Long serviceId, Long clientId, Appointment.AppointmentStatus status) {
        this.dataAgendada = dataAgendada;
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.status = status;
    }

    public Instant getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Instant dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Appointment.AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(Appointment.AppointmentStatus status) {
        this.status = status;
    }
}

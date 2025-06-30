package com.spring_app.demo.domain.dtos.Appointment;

import com.spring_app.demo.domain.entities.Appointment;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public class AppointmentByDateAndService {

    @NotNull
    Instant dataAgendada;

    @NotNull
    UUID serviceId;

    @NotNull
    UUID clientId;

    Appointment.AppointmentStatus status;

    public AppointmentByDateAndService() {};

    public AppointmentByDateAndService(Instant dataAgendada, UUID serviceId, UUID clientId, Appointment.AppointmentStatus status) {
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

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Appointment.AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(Appointment.AppointmentStatus status) {
        this.status = status;
    }
}

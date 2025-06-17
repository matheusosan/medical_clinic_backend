package com.spring_app.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tb_appointment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "tb_data_agendada")
    Instant dataAgendada;

    @ManyToOne()
    @JoinColumn(name = "service_id")
    Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @Column(name = "cancellation_reason")
    String cancellationReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AppointmentStatus status;

    public enum AppointmentStatus {
        AGENDADO,
        CANCELADO,
    }
}

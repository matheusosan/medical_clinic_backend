package com.spring_app.demo.dtos.Appointment;

import com.spring_app.demo.entities.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppointmentRequestDTO {

    Instant dataAgendada;
    Long serviceId;
    Long clientId;
    Appointment.AppointmentStatus status;


}

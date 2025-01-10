package com.spring_app.demo.dtos.Appointment;

import com.spring_app.demo.entities.Appointment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Schema( type = "instant", example = "2025-01-01T16:00:00Z")
    Instant dataAgendada;

    @Schema( type = "long", example = "1")
    @NotNull
    Long serviceId;

    @NotNull
    @Schema( type = "long", example = "1")
    Long clientId;

    Appointment.AppointmentStatus status;


}

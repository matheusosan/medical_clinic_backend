package com.spring_app.demo.domain.dtos.Appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


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
    UUID serviceId;

    @NotNull
    @Schema( type = "long", example = "1")
    UUID clientId;
}

package com.spring_app.demo.dtos.Service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceRequestDTO {

    @NotNull
    @Schema( type = "string", example = "Radiologia")
    String name;

    @NotNull
    @Schema( type = "double", example = "99.90")
    BigDecimal price;

}

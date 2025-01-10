package com.spring_app.demo.dtos.Client;

import com.spring_app.demo.entities.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @NotNull
    @Schema( type = "string", example = "John Doe")
    private String name;

    @NotNull
    @Schema( type = "string", example = "99999999999")
    private String cpf;

    @NotNull
    @Schema( type = "string", example = "example@mail.com")
    private String email;

    @NotNull
    @Schema( type = "string", example = "qwerty123")
    private String password;

    @NotNull
    @Schema( type = "string", example = "51999999999")
    private String phoneNumber;

    @NotNull
    @Schema( type = "string", example = "USER")
    private Client.UserRoles role;

    @NotNull
    @Schema( type = "localdate", example = "2000-12-12")
    private LocalDate birthDate;


}
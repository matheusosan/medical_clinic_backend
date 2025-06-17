package com.spring_app.demo.domain.dtos.Authentication;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

    @NotNull
    @Schema( type = "string", example = "mail@mail.com")
    private String email;

    @NotNull
    @Schema( type = "string", example = "senha123")
    private String password;
}

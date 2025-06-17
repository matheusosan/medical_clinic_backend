package com.spring_app.demo.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailPayloadDTO {

    private String clientName;
    private String email;
    private String appointmentDateTime;
    private String serviceName;

}

package com.spring_app.demo.dtos.Service;

import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class ServiceRequestDTO {

    String name;
    BigDecimal price;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

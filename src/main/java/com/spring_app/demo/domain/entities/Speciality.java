package com.spring_app.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_speciality")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "tb_name")
    String name;

    @Column(name = "tb_price")
    BigDecimal price;

    public Speciality(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}

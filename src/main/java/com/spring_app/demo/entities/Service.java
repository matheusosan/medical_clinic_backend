package com.spring_app.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "tb_name")
    String name;

    @Column(name = "tb_price")
    BigDecimal price;

    public Service(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}

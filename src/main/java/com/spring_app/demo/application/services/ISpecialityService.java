package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;

import java.util.List;

public interface ISpecialityService {
    List<Speciality> getAllServices();
    Speciality findById(Long id);
    Speciality createService(ServiceRequestDTO dto);
}

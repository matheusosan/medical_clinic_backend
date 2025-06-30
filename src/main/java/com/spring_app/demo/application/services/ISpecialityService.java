package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Service.ServiceResponseDTO;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ISpecialityService {
    List<ServiceResponseDTO> getAllServices();
    Speciality findById(UUID id);
    Speciality createService(ServiceRequestDTO dto);
}

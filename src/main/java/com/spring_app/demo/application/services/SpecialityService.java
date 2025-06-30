package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.domain.dtos.Service.ServiceResponseDTO;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.exceptions.ServiceExceptions.ServiceNotFoundException;
import com.spring_app.demo.infra.repositories.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialityService implements ISpecialityService{
    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<ServiceResponseDTO> getAllServices() {
        var specialitiesEntity = specialityRepository.findAll();

        var specialities = specialitiesEntity.stream()
                .map(entity -> {
                    ServiceResponseDTO dto = new ServiceResponseDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setPrice(entity.getPrice());
                    return dto;
                })
                .collect(Collectors.toList());

        return specialities;
    }

    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("Especialidade não encontrada."));
    }

    public Speciality createService(ServiceRequestDTO dto) {
        return specialityRepository.save(new Speciality(dto.getName(), dto.getPrice()));
    }
}

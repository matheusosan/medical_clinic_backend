package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.exceptions.ServiceExceptions.ServiceNotFoundException;
import com.spring_app.demo.infra.repositories.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService implements ISpecialityService{
    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> getAllServices() {
        return specialityRepository.findAll();
    }

    public Speciality findById(Long id) {
        return specialityRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("Especialidade não encontrada."));
    }

    public Speciality createService(ServiceRequestDTO dto) {
        return specialityRepository.save(new Speciality(dto.getName(), dto.getPrice()));
    }
}

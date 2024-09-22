package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.exceptions.ServiceExceptions.ServiceNotFoundException;
import com.spring_app.demo.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service findById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("Especialidade não encontrada."));
    }

    public Service createService(ServiceRequestDTO dto) {
        Service newService = new Service();

        newService.setName(dto.getName());
        newService.setPrice(dto.getPrice());

        return serviceRepository.save(newService);

    }
}

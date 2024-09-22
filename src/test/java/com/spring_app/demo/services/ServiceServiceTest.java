package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.exceptions.ServiceExceptions.ServiceNotFoundException;
import com.spring_app.demo.repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceServiceTest {

    @InjectMocks
    private ServiceService serviceService;

    @Mock
    private ServiceRepository repository;

    Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new Service();
        service.setId(1L);
        service.setName("Radiologia");
        service.setPrice(BigDecimal.valueOf(99.9));
    }

    @DisplayName("Should return all Services")
    @Test
    void getAllServices() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(service));

        List<Service> foundService = serviceService.getAllServices();

        assertNotNull(service);
        assertEquals("Radiologia", foundService.getFirst().getName());
    }

    @DisplayName("Should return a service by ID")
    @Test
    void findById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(service));

        Service foundService = serviceService.findById(1L);

        assertNotNull(foundService);
        assertEquals(service, foundService);
    }

    @DisplayName("Should throw an Exception if speciality was not found by ID")
    @Test
    void findByIdException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(service));

        assertThrows(ServiceNotFoundException.class, () -> serviceService.findById(2L));
        Mockito.verify(repository, Mockito.times(1));
    }

    @DisplayName("Should create a service succesfully")
    @Test
    void createService() {
        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setName("Odontologia");
        dto.setPrice(BigDecimal.valueOf(109.90));
        Service expectedService = new Service();
        expectedService.setName(dto.getName());
        expectedService.setPrice(dto.getPrice());

        Mockito.when(repository.save(Mockito.any(Service.class))).thenReturn(expectedService);

        Service createdService = serviceService.createService(dto);

        assertNotNull(createdService);
        assertEquals("Odontologia", createdService.getName());
        assertEquals(BigDecimal.valueOf(109.90), createdService.getPrice());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Service.class));
    }
}
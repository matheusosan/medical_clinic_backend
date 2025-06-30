package com.spring_app.demo.services;

import com.spring_app.demo.application.services.SpecialityService;
import com.spring_app.demo.domain.dtos.Service.ServiceRequestDTO;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.exceptions.ServiceExceptions.ServiceNotFoundException;
import com.spring_app.demo.infra.repositories.SpecialityRepository;
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

class SpecialitySpecialityTest {

    @InjectMocks
    private SpecialityService specialityService;

    @Mock
    private SpecialityRepository repository;

    Speciality speciality;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        speciality = new Speciality();
        speciality.setId(1L);
        speciality.setName("Radiologia");
        speciality.setPrice(BigDecimal.valueOf(99.9));
    }

//    @DisplayName("Should return all Services")
//    @Test
//    void getAllServices() {
//        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(speciality));
//
//        List<Speciality> foundSpeciality = specialityService.getAllServices();
//
//        assertNotNull(speciality);
//        assertEquals("Radiologia", foundSpeciality.getFirst().getName());
//    }

    @DisplayName("Should return a service by ID")
    @Test
    void findById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(speciality));

        Speciality foundSpeciality = specialityService.findById(1L);

        assertNotNull(foundSpeciality);
        assertEquals(speciality, foundSpeciality);
    }

    @DisplayName("Should throw an Exception if speciality was not found by ID")
    @Test
    void findByIdException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(speciality));

        assertThrows(ServiceNotFoundException.class, () -> specialityService.findById(2L));
        Mockito.verify(repository, Mockito.times(1));
    }

    @DisplayName("Should create a service succesfully")
    @Test
    void createService() {
        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setName("Odontologia");
        dto.setPrice(BigDecimal.valueOf(109.90));
        Speciality expectedSpeciality = new Speciality();
        expectedSpeciality.setName(dto.getName());
        expectedSpeciality.setPrice(dto.getPrice());

        Mockito.when(repository.save(Mockito.any(Speciality.class))).thenReturn(expectedSpeciality);

        Speciality createdSpeciality = specialityService.createService(dto);

        assertNotNull(createdSpeciality);
        assertEquals("Odontologia", createdSpeciality.getName());
        assertEquals(BigDecimal.valueOf(109.90), createdSpeciality.getPrice());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Speciality.class));
    }
}
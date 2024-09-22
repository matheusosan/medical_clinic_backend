package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.entities.Appointment;
import com.spring_app.demo.entities.Client;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.repositories.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Service service;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new Service();
        service.setId(1L);
        service.setName("Radiologia");
        service.setPrice(BigDecimal.valueOf(99.9));

        client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setEmail("john.doe@example.com");
        client.setCpf("12345678900");

        appointment = Appointment.builder()
                .id(1L)
                .client(client)
                .service(service)
                .dataAgendada(Instant.now())
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .build();
    }


    @Test
    void getAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(appointment));

        List<Appointment> foundAppointments = appointmentService.getAllAppointments();

        assertEquals("John Doe", foundAppointments.getFirst().getClient().getName());
        assertEquals("Radiologia", foundAppointments.getFirst().getService().getName());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void findAllByDateAndServiceId() {
    }

    @Test
    void findAllAppointmentsByUserId() {
    }

    @DisplayName("Should update the given appointment to CANCELED")
    @Test
    void cancelAppointmentSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.cancelAppointment(1L);

        assertEquals(Appointment.AppointmentStatus.CANCELED, appointment.getStatus());

        verify(appointmentRepository).save(appointment);
    }

    @DisplayName("Should create an appointment successfully")
    @Test
    void createAppointmentSuccess() {
        LocalDateTime date = LocalDateTime.of(2024, 9, 23, 10, 0);
        Instant validDate = date.toInstant(ZoneOffset.UTC);
        AppointmentRequestDTO dto = new AppointmentRequestDTO(validDate, service.getId(), client.getId(), Appointment.AppointmentStatus.SCHEDULED);

        when(clientService.findById(dto.getClientId())).thenReturn(client);
        when(serviceService.findById(dto.getServiceId())).thenReturn(service);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Appointment createdAppointment = appointmentService.createAppointment(dto);

        assertNotNull(createdAppointment);
        assertEquals(client.getName(), createdAppointment.getClient().getName());
        assertEquals(service.getName(), createdAppointment.getService().getName());
        assertEquals(dto.getDataAgendada(), createdAppointment.getDataAgendada());

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @DisplayName("Should throw an exception if the scheduled day is a Sunday")
    @Test
    void createAppointmentDayException() {
        LocalDateTime sundayDateTime = LocalDateTime.of(2024, 9, 22, 10, 0);
        Instant sundayInstant = sundayDateTime.toInstant(ZoneOffset.UTC);

        AppointmentRequestDTO dto = new AppointmentRequestDTO(sundayInstant, service.getId(), client.getId(), Appointment.AppointmentStatus.SCHEDULED);

        assertThrows(OutOfWorkingPeriodException.class, () -> appointmentService.createAppointment(dto));
    }

    @DisplayName("Should throw an exception if the scheduled time is not between 09:00 and 18:00")
    @Test
    void createAppointmentHourException() {
        LocalDateTime invalidHour = LocalDateTime.of(2024, 9, 25, 8, 0);
        Instant invalidInstant = invalidHour.toInstant(ZoneOffset.UTC);

        AppointmentRequestDTO dto = new AppointmentRequestDTO(invalidInstant, service.getId(), client.getId(), Appointment.AppointmentStatus.SCHEDULED);

        assertThrows(OutOfWorkingPeriodException.class, () -> appointmentService.createAppointment(dto));
    }
}
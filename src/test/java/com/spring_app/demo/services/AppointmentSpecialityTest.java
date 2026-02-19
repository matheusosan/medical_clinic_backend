package com.spring_app.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring_app.demo.application.services.AppointmentService;
import com.spring_app.demo.application.services.ClientService;
import com.spring_app.demo.application.services.SpecialityService;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.domain.entities.Appointment;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.infra.repositories.AppointmentRepository;
import com.spring_app.demo.application.services.IMessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentSpecialityTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private SpecialityService specialityService;

    @Mock
    private IMessagingService IMessagingService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Speciality speciality;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        speciality = new Speciality();
        speciality.setId(UUID.randomUUID());
        speciality.setName("Radiologia");
        speciality.setPrice(BigDecimal.valueOf(99.9));

        client = new Client();
        client.setId(UUID.randomUUID());
        client.setName("John Doe");
        client.setEmail("john.doe@example.com");
        client.setCpf("12345678900");

        appointment = Appointment.builder()
                .id(UUID.randomUUID())
                .client(client)
                .speciality(speciality)
                .dataAgendada(Instant.now())
                .status(Appointment.AppointmentStatus.CANCELADO)
                .build();
    }


    @Test
    void getAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(appointment));

        List<AppointmentResponseDTO> foundAppointments = appointmentService.getAllAppointments();

        assertEquals("John Doe", foundAppointments.getFirst().getClient().getName());
        assertEquals("Radiologia", foundAppointments.getFirst().getSpeciality().getName());
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
        UUID appointmentId = UUID.randomUUID();

        appointment.setId(appointmentId);

        when(appointmentRepository.findById(appointmentId))
                .thenReturn(Optional.of(appointment));

        appointmentService.cancelAppointment(appointmentId);

        assertEquals(Appointment.AppointmentStatus.CANCELADO, appointment.getStatus());
        assertEquals("Cancelado pelo usuário.", appointment.getCancellationReason());

        verify(appointmentRepository).save(appointment);
    }

    @DisplayName("Should create an appointment successfully")
    @Test
    void createAppointmentSuccess() throws JsonProcessingException {
        LocalDateTime date = LocalDateTime.of(2024, 9, 23, 10, 0);
        Instant validDate = date.toInstant(ZoneOffset.UTC);
        AppointmentRequestDTO dto = new AppointmentRequestDTO(validDate, speciality.getId(), client.getId());

        when(clientService.findById(dto.getClientId())).thenReturn(client);
        when(specialityService.findById(dto.getServiceId())).thenReturn(speciality);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Appointment createdAppointment = appointmentService.createAppointment(dto);

        assertNotNull(createdAppointment);
        assertEquals(client.getName(), createdAppointment.getClient().getName());
        assertEquals(speciality.getName(), createdAppointment.getSpeciality().getName());
        assertEquals(dto.getDataAgendada(), createdAppointment.getDataAgendada());

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @DisplayName("Should throw an exception if the scheduled day is a Sunday")
    @Test
    void createAppointmentDayException() {
        LocalDateTime sundayDateTime = LocalDateTime.of(2024, 9, 22, 10, 0);
        Instant sundayInstant = sundayDateTime.toInstant(ZoneOffset.UTC);

        AppointmentRequestDTO dto = new AppointmentRequestDTO(sundayInstant, speciality.getId(), client.getId());

        assertThrows(OutOfWorkingPeriodException.class, () -> appointmentService.createAppointment(dto));
    }

    @DisplayName("Should throw an exception if the scheduled time is not between 09:00 and 18:00")
    @Test
    void createAppointmentHourException() {
        LocalDateTime invalidHour = LocalDateTime.of(2024, 9, 25, 8, 0);
        Instant invalidInstant = invalidHour.toInstant(ZoneOffset.UTC);

        AppointmentRequestDTO dto = new AppointmentRequestDTO(invalidInstant, speciality.getId(), client.getId());

        assertThrows(OutOfWorkingPeriodException.class, () -> appointmentService.createAppointment(dto));
    }
}
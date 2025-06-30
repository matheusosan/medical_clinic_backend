package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Appointment.AppointmentByDateAndService;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.domain.dtos.Appointment.AppointmentResponseDTO;
import com.spring_app.demo.domain.dtos.Client.ClientResponseDTO;
import com.spring_app.demo.domain.dtos.EmailPayloadDTO;
import com.spring_app.demo.domain.entities.Appointment;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.domain.entities.Speciality;
import com.spring_app.demo.domain.exceptions.ScheduleExceptions.AppointmentNotFoundException;
import com.spring_app.demo.domain.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.infra.repositories.AppointmentRepository;
import com.spring_app.demo.utils.BusinessHoursUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final IClientService clientService;
    private final ISpecialityService specialityService;
    private final IMessagingService IMessagingService;

    public AppointmentService(AppointmentRepository appointmentRepository, IClientService clientService, ISpecialityService specialityService, IMessagingService IMessagingService) {
        this.appointmentRepository = appointmentRepository;
        this.clientService = clientService;
        this.specialityService = specialityService;
        this.IMessagingService = IMessagingService;
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        var appointmentsEntity = appointmentRepository.findAll();

        var appointments = appointmentsEntity.stream()
                .map(entity -> {
                    AppointmentResponseDTO dto = new AppointmentResponseDTO();
                    dto.setId(entity.getId());
                    dto.setDataAgendada(entity.getDataAgendada());
                    dto.setSpeciality(entity.getSpeciality());
                    dto.setClient(ClientResponseDTO.fromEntity(entity.getClient()));
                    dto.setCancellationReason(entity.getCancellationReason());
                    dto.setStatus(entity.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());

        return appointments;
    }

    public List<AppointmentByDateAndService> findAllByDateAndServiceId(LocalDate date, Long specialityId) {
        List<Appointment> appointments = appointmentRepository.findAllByDateAndServiceId(date, specialityId);
        return appointments.stream().map(appointment ->
                new AppointmentByDateAndService(appointment.getDataAgendada(), appointment.getSpeciality().getId(), appointment.getClient().getId(),appointment.getStatus()))
                    .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> findAllAppointmentsByUserId(Long id, String sortBy) {
        Sort sort = switch (sortBy.toLowerCase()) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "dataAgendada");
            case "newest" -> Sort.by(Sort.Direction.DESC, "dataAgendada");
            case "cheapest" -> Sort.by(Sort.Direction.ASC, "service.price");
            case "mostexpensive" -> Sort.by(Sort.Direction.DESC, "service.price");
            default -> throw new IllegalArgumentException("Invalid sort option: " + sortBy);
        };

        var appointmentsEntity = appointmentRepository.findAllAppointmentsByUserId(id, sort);

        var appointments = appointmentsEntity.stream()
                .map(entity -> {
                    AppointmentResponseDTO dto = new AppointmentResponseDTO();
                    dto.setId(entity.getId());
                    dto.setDataAgendada(entity.getDataAgendada());
                    dto.setSpeciality(entity.getSpeciality());
                    dto.setClient(ClientResponseDTO.fromEntity(entity.getClient()));
                    dto.setCancellationReason(entity.getCancellationReason());
                    dto.setStatus(entity.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());

        return appointments;
    }

    public void cancelAppointment(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setStatus(Appointment.AppointmentStatus.CANCELADO);
            appointment.setCancellationReason("Cancelado pelo usuário.");
            appointmentRepository.save(appointment);
            return;
        }
        throw new AppointmentNotFoundException("Agendamento não encontrado");

    }


    public Appointment createAppointment(AppointmentRequestDTO dto) {
        boolean isOpeningHours = BusinessHoursUtil.isOpeningHours(dto.getDataAgendada());

        String dayOfWeek = BusinessHoursUtil.getDayOfWeek(dto.getDataAgendada());

        if(dayOfWeek.equals("SUNDAY")) {
            throw new OutOfWorkingPeriodException("A data agendada deve ser de segunda a sábado.");
        }

        if(!isOpeningHours) {
            throw new OutOfWorkingPeriodException("Horário agendado está fora do horário de funcionamento!");
        }

        Client client = clientService.findById(dto.getClientId());
        Speciality speciality = specialityService.findById(dto.getServiceId());

        Appointment newAppointment = Appointment.builder()
                .speciality(speciality)
                .client(client)
                .dataAgendada(dto.getDataAgendada())
                .status(Appointment.AppointmentStatus.AGENDADO)
                .build();

       Appointment createdAppointment = appointmentRepository.save(newAppointment);

       IMessagingService.sendMessage("email_topic", new EmailPayloadDTO(client.getName(), client.getEmail(), dto.getDataAgendada().toString(), speciality.getName()));

       return createdAppointment;
    }
}

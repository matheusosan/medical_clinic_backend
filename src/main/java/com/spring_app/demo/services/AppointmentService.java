package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Appointment.AppointmentRequestDTO;
import com.spring_app.demo.entities.Appointment;
import com.spring_app.demo.entities.Client;
import com.spring_app.demo.entities.Service;
import com.spring_app.demo.exceptions.ScheduleExceptions.AppointmentNotFoundException;
import com.spring_app.demo.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring_app.demo.utils.BusinessHoursUtil;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceService serviceService;


    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<AppointmentRequestDTO> findAllByDateAndServiceId(LocalDate date, Long serviceId) {
        List<Appointment> appointments = appointmentRepository.findAllByDateAndServiceId(date, serviceId);
        return appointments.stream().map(appointment ->
                new AppointmentRequestDTO(
                        appointment.getDataAgendada(),
                        appointment.getService().getId(),
                        appointment.getClient().getId(),
                        appointment.getStatus()

                )
        ).collect(Collectors.toList());
    }

    public List<Appointment> findAllAppointmentsByUserId(Long id, String sortBy) {
        Sort sort = switch (sortBy.toLowerCase()) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "dataAgendada");
            case "newest" -> Sort.by(Sort.Direction.DESC, "dataAgendada");
            case "cheapest" -> Sort.by(Sort.Direction.ASC, "service.price");
            case "mostexpensive" -> Sort.by(Sort.Direction.DESC, "service.price");
            default -> throw new IllegalArgumentException("Invalid sort option: " + sortBy);
        };

        return appointmentRepository.findAllAppointmentsByUserId(id, sort);
    }

    public void cancelAppointment(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setStatus(Appointment.AppointmentStatus.CANCELADO);
            appointment.setCancellationReason("Cancelado pelo usuário.");
            appointmentRepository.save(appointment);
        } else {
            throw new AppointmentNotFoundException("Agendamento não encontrado");
        }
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
        Service service = serviceService.findById(dto.getServiceId());

        Appointment newAppointment = Appointment.builder()
                .service(service)
                .client(client)
                .dataAgendada(dto.getDataAgendada())
                .status(Appointment.AppointmentStatus.AGENDADO)
                .build();

       return appointmentRepository.save(newAppointment);
    }

}

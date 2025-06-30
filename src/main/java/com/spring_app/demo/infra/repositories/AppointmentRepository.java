package com.spring_app.demo.infra.repositories;

import com.spring_app.demo.domain.entities.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE DATE(a.dataAgendada) = :date AND a.speciality.id = :specialityId AND (a.status = 'AGENDADO')")
    List<Appointment> findAllByDateAndServiceId(@Param("date") LocalDate date, @Param("specialityId") UUID specialityId);

    @Query("SELECT a FROM Appointment a WHERE a.client.id = :userId")
    List<Appointment> findAllAppointmentsByUserId(@Param("userId") UUID userId, Sort sort);
}

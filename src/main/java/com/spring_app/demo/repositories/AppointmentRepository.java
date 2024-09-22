package com.spring_app.demo.repositories;

import com.spring_app.demo.entities.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE DATE(a.dataAgendada) = :date AND a.service.id = :serviceId")
    List<Appointment> findAllByDateAndServiceId(@Param("date") LocalDate date, @Param("serviceId") Long serviceId);

    @Query("SELECT a FROM Appointment a WHERE a.client.id = :userId")
    List<Appointment> findAllAppointmentsByUserId(@Param("userId") Long userId, Sort sort);
}

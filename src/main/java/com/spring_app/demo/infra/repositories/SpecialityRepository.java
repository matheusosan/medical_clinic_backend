package com.spring_app.demo.infra.repositories;

import com.spring_app.demo.domain.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}

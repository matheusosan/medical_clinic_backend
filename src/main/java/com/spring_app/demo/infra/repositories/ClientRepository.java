package com.spring_app.demo.infra.repositories;

import com.spring_app.demo.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByCpf(String cpf);

    UserDetails findByEmail(String email);

    Optional<Client> getByEmail(String email);
}

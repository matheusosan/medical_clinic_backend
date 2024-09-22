package com.spring_app.demo.repositories;

import com.spring_app.demo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpf(String cpf);

    UserDetails findByEmail(String email);

    Optional<Client> getByEmail(String email);
}

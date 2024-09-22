package com.spring_app.demo.services;

import com.spring_app.demo.dtos.Authentication.AuthenticationDTO;
import com.spring_app.demo.dtos.Authentication.LoginResponseDTO;
import com.spring_app.demo.entities.Client;
import com.spring_app.demo.repositories.ClientRepository;
import com.spring_app.demo.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    ClientService clientService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client user = this.clientService.getByEmail(username);
        return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    public String login(AuthenticationDTO dto) {
        Client user = this.clientService.getByEmail(dto.email());

        if(passwordEncoder.matches(dto.password(), user.getPassword())) {
            return tokenService.generateToken((Client) user);
        }

        throw new BadCredentialsException("Credenciais inválidas");

    }
}

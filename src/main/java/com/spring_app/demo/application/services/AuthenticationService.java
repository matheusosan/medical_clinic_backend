package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Authentication.AuthenticationDTO;
import com.spring_app.demo.domain.entities.Client;
import com.spring_app.demo.security.ITokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService, IAuthenticationService {
    private final IClientService clientService;
    private final ITokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(IClientService clientService, ITokenService tokenService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client user = this.clientService.getByEmail(username);
        return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    public String login(AuthenticationDTO dto) {
        Client user = this.clientService.getByEmail(dto.getEmail());

        if(passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return tokenService.generateToken(user);
        }

        throw new BadCredentialsException("Credenciais inválidas");

    }
}

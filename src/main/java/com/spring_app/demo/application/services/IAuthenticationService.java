package com.spring_app.demo.application.services;

import com.spring_app.demo.domain.dtos.Authentication.AuthenticationDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {
    UserDetails loadUserByUsername(String username);
    String login(AuthenticationDTO dto);
}

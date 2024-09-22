package com.spring_app.demo.controllers;

import com.spring_app.demo.dtos.Authentication.AuthenticationDTO;

import com.spring_app.demo.dtos.Authentication.LoginResponseDTO;
import com.spring_app.demo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO dto) {
        String token = this.authenticationService.login(dto);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    };



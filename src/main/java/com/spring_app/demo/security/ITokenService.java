package com.spring_app.demo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring_app.demo.domain.entities.Client;

public interface ITokenService {
    String generateToken(Client client);
    String validateToken(String token);
    DecodedJWT decodeToken(String token);
}

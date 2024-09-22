package com.spring_app.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring_app.demo.entities.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String token_secret;

    public String generateToken(Client client) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(token_secret);
            return JWT.create()
                    .withIssuer("matheusosan")
                    .withSubject(client.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }   catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token: ", exception);
        }


    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(token_secret);
            return JWT.require(algorithm)
                    .withIssuer("matheusosan")
                    .build()
                    .verify(token)
                    .getSubject();
        }   catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao validar token");

        }
    }

    public DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(token_secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao decodificar token: ", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}

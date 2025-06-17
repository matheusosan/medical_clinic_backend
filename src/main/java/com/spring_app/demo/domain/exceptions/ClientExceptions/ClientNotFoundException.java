package com.spring_app.demo.domain.exceptions.ClientExceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException() {
        super("Cliente não encontrado!");
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}

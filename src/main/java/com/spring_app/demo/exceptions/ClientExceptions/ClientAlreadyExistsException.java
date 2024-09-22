package com.spring_app.demo.exceptions.ClientExceptions;

public class ClientAlreadyExistsException extends RuntimeException{

    public ClientAlreadyExistsException() {
        super("Cliente já cadastrado com estes dados!");
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}

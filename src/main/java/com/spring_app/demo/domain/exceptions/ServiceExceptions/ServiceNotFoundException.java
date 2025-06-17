package com.spring_app.demo.domain.exceptions.ServiceExceptions;

public class ServiceNotFoundException extends RuntimeException{

        public ServiceNotFoundException() {
            super("Especialidade não encontrada!");
        }

        public ServiceNotFoundException(String message) {
            super(message);
        }


}
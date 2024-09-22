package com.spring_app.demo.exceptions.ServiceExceptions;

public class ServiceNotFoundException extends RuntimeException{

        public ServiceNotFoundException() {
            super("Especialidade não encontrada!");
        }

        public ServiceNotFoundException(String message) {
            super(message);
        }


}
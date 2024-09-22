package com.spring_app.demo.exceptions.ScheduleExceptions;

public class AppointmentNotFoundException extends RuntimeException{


    public AppointmentNotFoundException() {
        super("Agendamento não encontrado!");
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}

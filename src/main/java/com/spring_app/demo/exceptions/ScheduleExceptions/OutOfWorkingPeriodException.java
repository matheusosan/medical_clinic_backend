package com.spring_app.demo.exceptions.ScheduleExceptions;

public class OutOfWorkingPeriodException extends RuntimeException{


    public OutOfWorkingPeriodException() {
        super("Fora do horário de funcionamento!");
    }

    public OutOfWorkingPeriodException(String message) {
        super(message);
    }
}

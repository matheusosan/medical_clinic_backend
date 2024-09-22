package com.spring_app.demo.exceptions;

import com.spring_app.demo.dtos.ErrorResponseDTO;
import com.spring_app.demo.exceptions.ClientExceptions.ClientAlreadyExistsException;
import com.spring_app.demo.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.exceptions.ScheduleExceptions.AppointmentNotFoundException;
import com.spring_app.demo.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.exceptions.ServiceExceptions.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> clientNotFoundHandler(ClientNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    private ResponseEntity<ErrorResponseDTO> clientAlreadyExistsHandler(ClientAlreadyExistsException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(OutOfWorkingPeriodException.class)
    private ResponseEntity<ErrorResponseDTO> outOfWorkingPeriodHandler(OutOfWorkingPeriodException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> serviceNotFoundHandler(ServiceNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    private ResponseEntity<ErrorResponseDTO> outOfWorkingPeriodHandler(AppointmentNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorResponseDTO> outOfWorkingPeriodHandler(BadCredentialsException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}

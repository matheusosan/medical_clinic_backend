package com.spring_app.demo.domain.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.spring_app.demo.domain.dtos.ErrorResponseDTO;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientAlreadyExistsException;
import com.spring_app.demo.domain.exceptions.ClientExceptions.ClientNotFoundException;
import com.spring_app.demo.domain.exceptions.ScheduleExceptions.AppointmentNotFoundException;
import com.spring_app.demo.domain.exceptions.ScheduleExceptions.OutOfWorkingPeriodException;
import com.spring_app.demo.domain.exceptions.ServiceExceptions.ServiceNotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        var errors = new ArrayList<String>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        var body = ErrorModel.builder()
                .messages(errors)
                .statusCode(HttpStatus.BAD_REQUEST.toString()).build();

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

@Data
@Builder
@JsonPropertyOrder({ "statusCode", "message", "messages" })
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String statusCode;
    private String message;
    private Collection<String> messages;
}

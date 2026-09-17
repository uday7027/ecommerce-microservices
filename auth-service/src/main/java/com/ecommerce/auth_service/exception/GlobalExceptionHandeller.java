package com.ecommerce.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandeller {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handelEmailExists(EmailAlreadyExistsException ex){
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status",409,
                "message", ex.getMessage()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handelUserNotFound(UserNotFoundException ex){
        return Map.of(
                "timestamp",LocalDateTime.now(),
                "status",404,
                "message",ex.getMessage()
        );
    }
}

package com.license.smapp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.ws.Response;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ExceptionResponse> emailAlreadyTaken(EmailAlreadyTakenException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Email already taken");
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Not Found");
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }
}

package com.license.smapp.boundry.errorhandling;


import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlingController {

    Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequestException(BadRequestException ex)
    {
        LOGGER.error(ex.getMessage());
        return this.generateErrorDto(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleResourceNotFoundException(ResourceNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        return this.generateErrorDto(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(this.generateErrorDto(HttpStatus.UNAUTHORIZED, ex), HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorDto defaultHandler(Exception ex) {
//        LOGGER.error(ex.getMessage());
//        return this.generateErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex);
//    }


    private ErrorDto generateErrorDto(HttpStatus status, Exception ex)
    {
        return new ErrorDto(status.value(), ex.getMessage());
    }
}

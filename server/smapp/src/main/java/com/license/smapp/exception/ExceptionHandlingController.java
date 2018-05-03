//package com.license.smapp.exception;
//
//
//import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.annotation.Resource;
//import javax.xml.ws.Response;
//
//@ControllerAdvice
//public class ExceptionHandlingController {
//
//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    private ErrorDto handleBadRequestException(BadRequestException ex)
//    {
//        return this.generateErrorDto(HttpStatus.BAD_REQUEST, ex);
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ErrorDto handleResourceNotFoundException(ResourceNotFoundException ex) {
//        return this.generateErrorDto(HttpStatus.NOT_FOUND, ex);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorDto defaultHandler(Exception ex) {
//        return this.generateErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex);
//    }
//
//
//    private ErrorDto generateErrorDto(HttpStatus status, Exception ex)
//    {
//        return new ErrorDto(status.value(), ex.getMessage());
//    }
//}

package com.license.smapp.exception;

public class BadRequestException extends SmappGenericException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

package com.license.smapp.boundry.exceptions;

import com.license.smapp.boundry.exceptions.SmappGenericException;

public class BadRequestException extends SmappGenericException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

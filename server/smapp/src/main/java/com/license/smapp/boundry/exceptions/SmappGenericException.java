package com.license.smapp.boundry.exceptions;

public class SmappGenericException extends Exception {

    public SmappGenericException(String message) {
        super(message);
    }

    public SmappGenericException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

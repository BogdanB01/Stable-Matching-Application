package com.license.smapp.exception;

public class SmappGenericException extends Exception {

    public SmappGenericException(String message) {
        super(message);
    }

    public SmappGenericException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

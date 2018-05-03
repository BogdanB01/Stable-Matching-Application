package com.license.smapp.exception;

public class ResourceNotFoundException extends SmappGenericException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

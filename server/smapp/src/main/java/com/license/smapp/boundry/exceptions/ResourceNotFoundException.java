package com.license.smapp.boundry.exceptions;

import com.license.smapp.boundry.exceptions.SmappGenericException;

public class ResourceNotFoundException extends SmappGenericException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

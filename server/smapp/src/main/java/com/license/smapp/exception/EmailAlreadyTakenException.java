package com.license.smapp.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    private String email;

    public EmailAlreadyTakenException(String email, String message) {
        super(message);
        this.email = email;
    }
}

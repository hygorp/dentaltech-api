package com.dentaltechapi.services.exceptions.specialty;

public class SpecialtyNotFoundException extends RuntimeException {
    public SpecialtyNotFoundException(String message) {
        super(message);
    }

    public SpecialtyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

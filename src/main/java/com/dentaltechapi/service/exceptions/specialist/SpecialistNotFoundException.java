package com.dentaltechapi.service.exceptions.specialist;

public class SpecialistNotFoundException extends RuntimeException {
    public SpecialistNotFoundException(String message) {
        super(message);
    }

    public SpecialistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

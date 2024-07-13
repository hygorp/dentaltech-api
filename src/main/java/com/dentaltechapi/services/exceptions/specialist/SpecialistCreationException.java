package com.dentaltechapi.services.exceptions.specialist;

public class SpecialistCreationException extends RuntimeException {
    public SpecialistCreationException(String message) {
        super(message);
    }

    public SpecialistCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

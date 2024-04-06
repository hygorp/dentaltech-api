package com.dentaltechapi.service.exceptions.specialist;

public class SpecialistCreationException extends RuntimeException {
    public SpecialistCreationException(String message) {
        super(message);
    }

    public SpecialistCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

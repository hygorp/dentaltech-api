package com.dentaltechapi.services.exceptions.specialist;

public class SpecialistUpdateException extends RuntimeException {
    public SpecialistUpdateException(String message) {
        super(message);
    }

    public SpecialistUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

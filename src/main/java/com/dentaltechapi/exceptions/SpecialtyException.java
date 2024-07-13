package com.dentaltechapi.exceptions;

public class SpecialtyException extends RuntimeException {
    public SpecialtyException() {
    }

    public SpecialtyException(String message) {
        super(message);
    }

    public SpecialtyException(String message, Throwable cause) {
        super(message, cause);
    }
}

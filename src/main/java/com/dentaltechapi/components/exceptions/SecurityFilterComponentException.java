package com.dentaltechapi.components.exceptions;

public class SecurityFilterComponentException extends RuntimeException {
    public SecurityFilterComponentException(String message) {
        super(message);
    }

    public SecurityFilterComponentException(String message, Throwable cause) {
        super(message, cause);
    }
}

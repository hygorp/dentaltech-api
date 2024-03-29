package com.dentaltechapi.service.exceptions;

public class AuthorizationServiceException extends RuntimeException {
    public AuthorizationServiceException(String message) {
        super(message);
    }

    public AuthorizationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.dentaltechapi.services.exceptions.user.session;

public class SessionUpdateException extends RuntimeException {
    public SessionUpdateException(String message) {
        super(message);
    }

    public SessionUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

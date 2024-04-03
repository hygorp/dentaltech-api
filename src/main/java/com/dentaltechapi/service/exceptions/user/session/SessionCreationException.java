package com.dentaltechapi.service.exceptions.user.session;

public class SessionCreationException extends RuntimeException{
    public SessionCreationException(String message) {
        super(message);
    }

    public SessionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

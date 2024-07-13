package com.dentaltechapi.services.exceptions.phone;

public class PhoneCreationException extends RuntimeException {
    public PhoneCreationException(String message) {
        super(message);
    }

    public PhoneCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

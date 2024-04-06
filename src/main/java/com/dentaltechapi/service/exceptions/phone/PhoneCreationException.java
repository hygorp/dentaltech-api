package com.dentaltechapi.service.exceptions.phone;

public class PhoneCreationException extends RuntimeException {
    public PhoneCreationException(String message) {
        super(message);
    }

    public PhoneCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

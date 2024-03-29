package com.dentaltechapi.service.exceptions;

public class AccountRecoveryException extends RuntimeException {
    public AccountRecoveryException(String message) {
        super(message);
    }

    public AccountRecoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}

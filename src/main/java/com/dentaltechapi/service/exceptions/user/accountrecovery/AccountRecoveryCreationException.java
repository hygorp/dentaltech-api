package com.dentaltechapi.service.exceptions.user.accountrecovery;

public class AccountRecoveryCreationException extends RuntimeException {
    public AccountRecoveryCreationException(String message) {
        super(message);
    }

    public AccountRecoveryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

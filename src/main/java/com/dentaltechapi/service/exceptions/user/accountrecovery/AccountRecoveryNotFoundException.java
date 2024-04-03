package com.dentaltechapi.service.exceptions.user.accountrecovery;

public class AccountRecoveryNotFoundException extends RuntimeException {
    public AccountRecoveryNotFoundException(String message) {
        super(message);
    }

    public AccountRecoveryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

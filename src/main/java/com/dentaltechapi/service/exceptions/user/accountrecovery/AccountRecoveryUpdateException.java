package com.dentaltechapi.service.exceptions.user.accountrecovery;

public class AccountRecoveryUpdateException extends RuntimeException {
    public AccountRecoveryUpdateException(String message) {
        super(message);
    }

    public AccountRecoveryUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}

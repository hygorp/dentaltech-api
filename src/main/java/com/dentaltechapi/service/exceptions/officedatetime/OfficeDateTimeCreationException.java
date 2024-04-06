package com.dentaltechapi.service.exceptions.officedatetime;

public class OfficeDateTimeCreationException extends RuntimeException {
    public OfficeDateTimeCreationException(String message) {
        super(message);
    }

    public OfficeDateTimeCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

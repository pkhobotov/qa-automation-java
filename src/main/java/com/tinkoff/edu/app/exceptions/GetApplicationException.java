package com.tinkoff.edu.app.exceptions;

public class GetApplicationException extends RuntimeException {
    public GetApplicationException(String message, Throwable cause) {
        super(message,
                cause);
    }
}

package com.tinkoff.edu.app.exceptions;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}

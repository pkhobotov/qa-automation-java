package com.tinkoff.edu.app.exceptions;

public class GetApplicationException extends RequestException{
    public GetApplicationException(String message, Throwable cause) {
        super(message,
              cause);
    }
}

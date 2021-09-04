package com.tinkoff.edu.app.exceptions;

public class IllegalRequestAmountException extends RequestException{
    public IllegalRequestAmountException(String message) {
        super(message);
    }
}

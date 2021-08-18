package com.tinkoff.edu.app;

public class LoanResponse {
    public String toString() {
        return this.type + " ";
    }

    private final ResponseType type;

    public LoanResponse(ResponseType type) {
        this.type = type;
    }

    public enum ResponseType {
        APPROVED, DENIED
    }
}

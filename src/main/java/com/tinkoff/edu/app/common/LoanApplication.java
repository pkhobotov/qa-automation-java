package com.tinkoff.edu.app.common;

import java.util.UUID;

public class LoanApplication {
    private final UUID requestId;
    private final LoanRequest request;
    private final ResponseType response;

    public LoanApplication(UUID requestId, LoanRequest request, ResponseType response) {
        this.requestId = requestId;
        this.request = request;
        this.response = response;
    }

    public ResponseType getResponse() {
        return response;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String toString() {
        return String.format("requestId %s : %s\nResult: %s",
                             this.requestId,
                             this.request,
                             this.response);
    }
}

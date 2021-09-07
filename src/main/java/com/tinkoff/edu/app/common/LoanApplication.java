package com.tinkoff.edu.app.common;

import java.util.UUID;

public class LoanApplication {
    private final LoanRequest request;
    private UUID requestId;
    private ResponseType response;

    public LoanApplication(LoanRequest request) {
        this.request = request;
    }

    public LoanRequest getRequest() {
        return request;
    }

    public ResponseType getResponse() {
        return response;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String toString() {
        return String.format("requestId %s : %s\nResult: %s",
                             this.requestId,
                             this.request,
                             this.response);
    }
}

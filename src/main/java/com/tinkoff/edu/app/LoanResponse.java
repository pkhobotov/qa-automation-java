package com.tinkoff.edu.app;

import java.util.UUID;

public class LoanResponse {
    private final UUID requestId;
    private final LoanRequest request;
    private final ResponseType responseType;

    public LoanResponse(UUID requestId, LoanRequest request, ResponseType responseType) {
        this.requestId = requestId;
        this.request = request;
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String toString() {
        return String.format("requestId %s : %s\nResult: %s",
                             this.requestId,
                             this.request,
                             this.responseType);
    }
}

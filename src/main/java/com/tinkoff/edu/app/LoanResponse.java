package com.tinkoff.edu.app;

public class LoanResponse {
    private final int requestId;
    private final LoanRequest request;
    private ResponseType responseType;

    public LoanResponse(int requestId, LoanRequest request, ResponseType responseType) {
        this.requestId = requestId;
        this.request = request;
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public int getRequestId() {
        return requestId;
    }

    public LoanRequest getRequest() {
        return request;
    }

    public String toString() {
        return String.format("requestId %s : %s\nResult: %s",
                             this.requestId,
                             this.request,
                             this.responseType);
    }
}

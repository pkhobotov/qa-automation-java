package com.tinkoff.edu.app;

public class LoanResponse {
    private final int requestId;
    private final LoanRequest request;
    private ResponseType type;

    public LoanResponse(int requestId, LoanRequest request) {
        this.requestId = requestId;
        this.request = request;
        this.type = LoanCalcService.calculateLoanResponse(request);
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
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
                             this.type);
    }
}

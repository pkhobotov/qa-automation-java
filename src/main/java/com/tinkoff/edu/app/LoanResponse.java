package com.tinkoff.edu.app;

public class LoanResponse {
    public ResponseType createResponse(LoanRequest loanRequest) {
        if (loanRequest.getMonths() > 8 || loanRequest.getAmount() > 1200) {
            return ResponseType.DENIED;
        } else {
            return ResponseType.APPROVED;
        }
    }

    private enum ResponseType {
        APPROVED, DENIED
    }
}

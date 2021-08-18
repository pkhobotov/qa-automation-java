package com.tinkoff.edu.app;

public class LoanCalcService {
    /**
     * TODO Loan calculation.
     */
    public void calculateLoan(LoanRequest request) {
        if (request.getMonths() > 8 || request.getAmount() > 1200) {
            request.setResponseType(LoanResponse.ResponseType.DENIED);
        } else {
            request.setResponseType(LoanResponse.ResponseType.APPROVED);
        }
    }
}

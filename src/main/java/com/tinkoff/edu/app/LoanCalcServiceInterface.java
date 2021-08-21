package com.tinkoff.edu.app;

public interface LoanCalcServiceInterface {
    LoanResponse createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);
}

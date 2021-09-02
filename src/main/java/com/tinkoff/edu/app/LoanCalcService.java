package com.tinkoff.edu.app;

public interface LoanCalcService {
    LoanApplication createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);
}

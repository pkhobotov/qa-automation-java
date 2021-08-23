package com.tinkoff.edu.app;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);
}

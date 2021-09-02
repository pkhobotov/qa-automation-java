package com.tinkoff.edu.app;

import java.util.UUID;

public interface LoanCalcService {
    LoanApplication createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);
    ResponseType getApplicationStatus(UUID requestId);
    ResponseType setApplicationStatus(UUID requestId, ResponseType response);
}

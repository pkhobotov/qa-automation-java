package com.tinkoff.edu.app;

import java.util.UUID;

public interface LoanCalcController {
    LoanApplication createRequest(LoanRequest request);
    ResponseType getApplicationStatus(UUID requestId);
    ResponseType setApplicationStatus(UUID requestId, ResponseType response);
}

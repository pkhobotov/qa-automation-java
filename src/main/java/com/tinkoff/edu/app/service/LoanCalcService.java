package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.UUID;

public interface LoanCalcService {
    UUID createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);

    ResponseType getApplicationStatus(UUID requestId);

    ResponseType setApplicationStatus(UUID requestId, ResponseType response);
}

package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.List;
import java.util.UUID;

public interface LoanCalcService {
    UUID createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);

    ResponseType getApplicationStatus(UUID requestId);

    ResponseType setApplicationStatus(UUID requestId, ResponseType response);

    List<LoanApplication> getApplicationsByRequesterType(Requester requester);

    double sumLoanAmountByRequesterType(Requester requester);
}

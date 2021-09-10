package com.tinkoff.edu.app.controller;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.List;
import java.util.UUID;

public interface LoanCalcController {
    UUID createRequest(LoanRequest request);

    ResponseType getApplicationStatus(UUID requestId);

    ResponseType setApplicationStatus(UUID requestId, ResponseType response);

    double sumLoanAmountByRequesterType(Requester requester);

    List<LoanApplication> getApplicationsByRequesterType(Requester requester);
}

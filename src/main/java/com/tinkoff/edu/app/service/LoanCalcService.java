package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.GetApplicationException;
import com.tinkoff.edu.app.exceptions.RequestException;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface LoanCalcService {
    LoanApplication createRequest(LoanRequest request);

    ResponseType calculateLoanResponse(LoanRequest request);

    ResponseType getApplicationStatus(UUID requestId);

    ResponseType setApplicationStatus(UUID requestId, ResponseType response);
}

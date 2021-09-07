package com.tinkoff.edu.app.controller;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.RequestException;

import java.util.UUID;

public interface LoanCalcController {
    UUID createRequest(LoanRequest request) throws RequestException;

    ResponseType getApplicationStatus(UUID requestId);

    ResponseType setApplicationStatus(UUID requestId, ResponseType response);
}

package com.tinkoff.edu.app.controller;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.*;
import com.tinkoff.edu.app.service.LoanCalcService;

import java.util.NoSuchElementException;
import java.util.UUID;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    @Override
    public LoanApplication createRequest(LoanRequest request) throws RequestException {
        if (request == null || request.getAmount() <= 0 || request.getMonths() <= 0)
            throw new IllegalArgumentException();
        if (request.getFio().length() < 10 || request.getFio().length() > 100)
            throw new FIOLengthException("Name length shorter then 10 or longer then 100!");
        if (!request.getFio().matches("[A-я\\-\\s]*"))
            throw new IllegalCharacterException("FIO may contain only alphabetical and dash");
        if (request.getAmount() > 999999.99 || request.getAmount() < 0.01)
            throw new IllegalRequestAmountException("Amount should be between 0.01 and 999999.99");
        return loanCalcService.createRequest(request);
    }

    @Override
    public ResponseType getApplicationStatus(UUID requestId) throws GetApplicationException {
        try {
            return loanCalcService.getApplicationStatus(requestId);
        } catch (NoSuchElementException e) {
            throw new GetApplicationException("No application for this ID",
                                              e);
        }
    }

    @Override
    public ResponseType setApplicationStatus(UUID requestId, ResponseType response) throws GetApplicationException {
        try {
            return loanCalcService.setApplicationStatus(requestId,
                                                        response);
        } catch (NoSuchElementException e) {
            throw new GetApplicationException("No application for this ID",
                                              e);
        }
    }
}
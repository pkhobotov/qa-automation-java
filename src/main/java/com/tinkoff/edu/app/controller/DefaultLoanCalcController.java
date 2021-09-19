package com.tinkoff.edu.app.controller;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.FIOLengthException;
import com.tinkoff.edu.app.exceptions.IllegalCharacterException;
import com.tinkoff.edu.app.exceptions.IllegalRequestAmountException;
import com.tinkoff.edu.app.exceptions.LoanTypeException;
import com.tinkoff.edu.app.service.LoanCalcService;

import java.util.List;
import java.util.UUID;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    @Override
    public UUID createRequest(LoanRequest request) {
        validate(request);
        return loanCalcService.createRequest(request);
    }

    private void validate(LoanRequest request) {
        if (request == null || request.getAmount() <= 0 || request.getMonths() <= 0)
            throw new IllegalArgumentException();
        if (request.getType() == null) throw new LoanTypeException("No LoanType received");
        String fio = request.getFio();
        if (fio == null || fio.length() < 10 || fio.length() > 100)
            throw new FIOLengthException("Name length shorter then 10 or longer then 100!");
        if (!request.getFio().matches("[A-Za-zА-я\\s-]*"))
            throw new IllegalCharacterException("FIO may contain only alphabetical and dash");
        if (request.getAmount() > 999999.99 || request.getAmount() < 0.01)
            throw new IllegalRequestAmountException("Amount should be between 0.01 and 999999.99");
    }

    @Override
    public ResponseType getApplicationStatus(UUID requestId) {
        return loanCalcService.getApplicationStatus(requestId);
    }

    @Override
    public ResponseType setApplicationStatus(UUID requestId, ResponseType response) {
        return loanCalcService.setApplicationStatus(requestId,
                response);
    }

    @Override
    public double sumLoanAmountByRequesterType(Requester requester) {
        return loanCalcService.sumLoanAmountByRequesterType(requester);
    }

    @Override
    public List<LoanApplication> getApplicationsByRequesterType(Requester requester) {
        return loanCalcService.getApplicationsByRequesterType(requester);
    }
}

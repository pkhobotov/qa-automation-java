package com.tinkoff.edu.app;

import java.util.UUID;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    @Override
    public LoanApplication createRequest(LoanRequest request) {
        if (request == null || request.getAmount() <= 0 || request.getMonths() <= 0)
            throw new IllegalArgumentException();
        return loanCalcService.createRequest(request);
    }

    @Override
    public ResponseType getApplicationStatus(UUID requestId) {
        return loanCalcService.getApplicationStatus(requestId);
    }

    @Override
    public ResponseType setApplicationStatus(UUID requestId, ResponseType response) {
        return loanCalcService.setApplicationStatus(requestId, response);
    }
}

package com.tinkoff.edu.app;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    @Override
    public LoanResponse createRequest(LoanRequest request) {
        if (request == null || request.getAmount() <= 0 || request.getMonths() <= 0)
            throw new IllegalArgumentException();
        return loanCalcService.createRequest(request);
    }
}

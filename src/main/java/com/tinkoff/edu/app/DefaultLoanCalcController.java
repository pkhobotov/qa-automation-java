package com.tinkoff.edu.app;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;
    }

    @Override
    public LoanResponse createRequest(LoanRequest request) {
        if (request == null) throw new IllegalArgumentException();
        LoanCalcLogger.log("got request");
        return loanCalcService.createRequest(request);
    }
}

package com.tinkoff.edu.app;

public class DefaultLoanCalcController implements LoanCalcController {
    protected LoanCalcService loanCalcService;

    public DefaultLoanCalcController(LoanCalcRepository repo) {
        this.loanCalcService = new DefaultLoanCalcService(repo);
    }

    @Override
    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcLogger.log("got request");
        return loanCalcService.createRequest(request);
    }
}

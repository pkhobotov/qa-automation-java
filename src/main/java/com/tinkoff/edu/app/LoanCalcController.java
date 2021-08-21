package com.tinkoff.edu.app;

public class LoanCalcController {
    private final LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcRepositoryInterface repo) {
        this.loanCalcService = new IpNotFriendlyCalcService(repo);
    }

    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcLogger.log("got request");
        return loanCalcService.createRequest(request);
    }
}

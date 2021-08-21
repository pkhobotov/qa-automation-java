package com.tinkoff.edu.app;

public class LoanCalcController implements LoanCalcControllerInterface {
    private final LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcRepositoryInterface repo) {
        this.loanCalcService = new IpNotFriendlyCalcService(repo);
    }

    @Override
    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcLogger.log("got request");
        return loanCalcService.createRequest(request);
    }
}

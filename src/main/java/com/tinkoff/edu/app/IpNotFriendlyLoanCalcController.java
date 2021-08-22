package com.tinkoff.edu.app;

public class IpNotFriendlyLoanCalcController extends DefaultLoanCalcController {
    protected LoanCalcService loanCalcService;

    public IpNotFriendlyLoanCalcController(LoanCalcRepository repo) {
        super(repo);// todo override with IpNotFriendlyService
        this.loanCalcService = new IpNotFriendlyLoanCalcService(repo);

    }

}

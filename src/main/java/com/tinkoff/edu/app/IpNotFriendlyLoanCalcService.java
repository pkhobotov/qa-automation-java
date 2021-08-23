package com.tinkoff.edu.app;

public class IpNotFriendlyLoanCalcService extends DefaultLoanCalcService {
    public IpNotFriendlyLoanCalcService(LoanCalcRepository repo) {
        super(repo);
    }

    @Override
    public ResponseType calculateLoanResponse(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) return ResponseType.DENIED;
        return super.calculateLoanResponse(request);
    }
}

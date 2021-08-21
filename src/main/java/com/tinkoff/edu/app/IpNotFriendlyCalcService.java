package com.tinkoff.edu.app;

public class IpNotFriendlyCalcService extends LoanCalcService {
    public IpNotFriendlyCalcService(LoanCalcRepositoryInterface repo) {
        super(repo);
    }

    @Override
    public ResponseType calculateLoanResponse(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) return ResponseType.DENIED;
        return super.calculateLoanResponse(request);
    }
}

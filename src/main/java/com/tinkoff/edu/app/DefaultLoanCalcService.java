package com.tinkoff.edu.app;

public class DefaultLoanCalcService implements LoanCalcService {
    protected LoanCalcRepository repo;

    public DefaultLoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    public LoanResponse createRequest(LoanRequest request) {
        ResponseType responseType = this.calculateLoanResponse(request);
        int requestId = this.repo.save(request);
        return new LoanResponse(requestId,
                                request,
                                responseType);
    }

    public ResponseType calculateLoanResponse(LoanRequest request) {
        if (request.getAmount() > 10_000 || request.getMonths() > 12) {
            return ResponseType.DENIED;
        } else {
            return ResponseType.APPROVED;
        }
    }
}

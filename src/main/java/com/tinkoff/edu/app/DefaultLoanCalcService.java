package com.tinkoff.edu.app;

import java.util.UUID;

public class DefaultLoanCalcService implements LoanCalcService {
    protected LoanCalcRepository repo;

    public DefaultLoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    public LoanResponse createRequest(LoanRequest request) {
        ResponseType responseType = this.calculateLoanResponse(request);
        UUID requestId = this.repo.save(request);
        return new LoanResponse(requestId,
                                request,
                                responseType);
    }

    public ResponseType calculateLoanResponse(LoanRequest request) {
        switch (request.getType()) {
            case OOO:
                if (request.getAmount() > 10_000 && request.getMonths() < 12) {
                    return ResponseType.APPROVED;
                }
            case PERSON:
                if (request.getAmount() <= 10_000 && request.getMonths() <= 12) {
                    return ResponseType.APPROVED;
                }
            default:
                return ResponseType.DENIED;
        }
    }
}

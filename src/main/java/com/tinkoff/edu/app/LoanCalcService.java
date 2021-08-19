package com.tinkoff.edu.app;

public class LoanCalcService {
    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcRepository calcRepository = new LoanCalcRepository();
        ResponseType responseType = calculateLoanResponse(request);
        int requestId = calcRepository.save(request);
        return new LoanResponse(requestId,
                                request,
                                responseType);
    }

    private ResponseType calculateLoanResponse(LoanRequest request) {
        if (request.getAmount() > 1200 || request.getMonths() > 10) {
            return ResponseType.DENIED;
        } else {
            return ResponseType.APPROVED;
        }
    }
}

package com.tinkoff.edu.app;

public class LoanCalcService {
    /**
     * TODO Loan calculation.
     */
    private LoanResponse response;
    private LoanRequest request;

    public LoanResponse createRequest(LoanRequest request) {
        this.request = request;
        LoanCalcRepository calcRepository = new LoanCalcRepository();
        int requestId = calcRepository.save(request);
        this.response = new LoanResponse(requestId,
                                         request);
        return response;
    }
}

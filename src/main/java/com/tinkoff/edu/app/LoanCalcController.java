package com.tinkoff.edu.app;

public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     *
     */
    public LoanCalcController() {
    }

    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcService calcServices = new LoanCalcService();
        return calcServices.createRequest(request);
    }
}

package com.tinkoff.edu.app;

public class LoanCalcService {
    /**
     * TODO Loan calculation.
     *
     * @param request
     */
    public int createRequest(LoanRequest request) {
        LoanCalcRepository calcRepository = new LoanCalcRepository();
        return calcRepository.save(request);
    }
}

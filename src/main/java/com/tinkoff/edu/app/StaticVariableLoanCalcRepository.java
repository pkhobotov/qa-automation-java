package com.tinkoff.edu.app;

public class StaticVariableLoanCalcRepository implements LoanCalcRepositoryInterface {
    private static int requestId;

    @Override
    public int save(LoanRequest request) {
        LoanCalcLogger.log("request saved");
        return ++requestId;
    }
}

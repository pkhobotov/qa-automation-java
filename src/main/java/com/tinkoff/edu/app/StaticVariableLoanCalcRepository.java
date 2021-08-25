package com.tinkoff.edu.app;

public class StaticVariableLoanCalcRepository implements LoanCalcRepository {
    public int requestId;

    @Override
    public int save(LoanRequest request) {
        LoanCalcLogger.log("request saved");
        return ++requestId;
    }
}

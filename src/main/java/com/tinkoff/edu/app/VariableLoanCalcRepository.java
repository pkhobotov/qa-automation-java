package com.tinkoff.edu.app;

public class VariableLoanCalcRepository implements LoanCalcRepository {
    private int requestId;

    @Override
    public int save(LoanRequest request) {
//        LoanCalcLogger.log("request saved");
        return ++requestId;
    }
}

package com.tinkoff.edu.app;

public class StaticVariableLoanCalcRepository implements LoanCalcRepository {
    public static int requestId;

    @Override
    public int save(LoanRequest request) {
        LoanCalcLogger.log("request saved");
        return ++requestId;
    }

    @Override
    public void setRequestId(int id) {
        requestId = id;
    }
}

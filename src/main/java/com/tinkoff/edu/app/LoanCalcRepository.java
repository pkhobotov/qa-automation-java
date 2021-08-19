package com.tinkoff.edu.app;

public class LoanCalcRepository {
    private static int requestId;

    public int save(LoanRequest request) {
        LoanCalcLogger.log("request saved");
        return ++requestId;
    }
}

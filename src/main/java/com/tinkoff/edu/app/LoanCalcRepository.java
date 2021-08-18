package com.tinkoff.edu.app;

public class LoanCalcRepository {
    private static int requestId;

    public int save() {
        return ++requestId;
    }
}

package com.tinkoff.edu.app;

public class LoanCalcRepository {
    private static int requestId;

    public static int save() {
        return ++requestId;
    }
}

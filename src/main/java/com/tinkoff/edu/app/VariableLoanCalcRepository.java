package com.tinkoff.edu.app;

import java.util.UUID;

public class VariableLoanCalcRepository implements LoanCalcRepository {
    @Override
    public UUID save(LoanRequest request) {
        return UUID.randomUUID();
    }
}

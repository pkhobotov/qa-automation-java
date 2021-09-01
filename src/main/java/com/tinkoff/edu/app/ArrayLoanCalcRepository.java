package com.tinkoff.edu.app;

import java.util.UUID;

public class ArrayLoanCalcRepository implements LoanCalcRepository {
    public static UUID[] arrayRepository = new UUID[100_000];
    private static int position = 0;
    private UUID requestId;

    @Override
    public UUID save(LoanRequest request) {
        requestId = UUID.randomUUID();
        arrayRepository[position++] = requestId;
        return requestId;
    }
}

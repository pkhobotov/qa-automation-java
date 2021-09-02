package com.tinkoff.edu.app;

import java.util.Arrays;
import java.util.UUID;

public class StaticArrayLoanCalcRepository implements LoanCalcRepository {
    public static UUID[] arrayRepository = new UUID[10];
    private static int position = 0;
    private UUID requestId;

    @Override
    public UUID save(LoanRequest request) {
        requestId = UUID.randomUUID();
        arrayRepository[position++] = requestId;
//        System.out.println(requestId);
//        System.out.println(Arrays.toString(arrayRepository));
        return requestId;
    }
}

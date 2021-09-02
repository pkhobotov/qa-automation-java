package com.tinkoff.edu.app;

import java.util.UUID;

public class ArrayLoanCalcRepository implements LoanCalcRepository {
    public LoanCalcRow[] arrayRepository = new LoanCalcRow[100000];
    private int position;
    private UUID requestId;

    @Override
    public UUID save(LoanRequest request, ResponseType response) {
        requestId = UUID.randomUUID();
        LoanCalcRow row = new LoanCalcRow(requestId,
                                          response);
        arrayRepository[position++] = row;
        return requestId;
    }

    public LoanCalcRow getRowById(UUID requestId) {
        for (int i = 0; i < position; i++) {
            if ( arrayRepository[i].getRequestId().equals(requestId)) {
                return arrayRepository[i];
            }
        }
        return null;
    }
}
//UUID responseType

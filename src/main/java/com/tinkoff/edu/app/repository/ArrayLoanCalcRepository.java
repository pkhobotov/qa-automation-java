package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanCalcRow;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.NoSuchElementException;
import java.util.UUID;

public class ArrayLoanCalcRepository implements LoanCalcRepository {
    private final LoanCalcRow[] arrayRepository = new LoanCalcRow[100000];
    private int position;

    @Override
    public UUID save(LoanRequest request, ResponseType response){
        UUID requestId = UUID.randomUUID();
        LoanCalcRow row = new LoanCalcRow(requestId,
                                          response);
        try {
            arrayRepository[position++] = row;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Repository is Full");
        }
        return requestId;
    }

    public LoanCalcRow getRowById(UUID requestId) throws NoSuchElementException {
        for (int i = 0; i < position; i++) {
            if (arrayRepository[i].getRequestId().equals(requestId)) {
                return arrayRepository[i];
            }
        }
        throw new NoSuchElementException("No element with given ID");
    }
}

package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanCalcRow;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.UUID;

public interface LoanCalcRepository {
    UUID save(LoanRequest request, ResponseType response) throws ArrayIndexOutOfBoundsException;

    LoanCalcRow getRowById(UUID requestId);
}

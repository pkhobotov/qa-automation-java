package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanCalcRow;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

public class DefaultLoanCalcService implements LoanCalcService {
    protected LoanCalcRepository repo;

    public DefaultLoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    public LoanApplication createRequest(LoanRequest request) {
        ResponseType responseType = this.calculateLoanResponse(request);
        UUID requestId = this.repo.save(request,
                                        responseType);
        return new LoanApplication(requestId,
                                   request,
                                   responseType);
    }

    public ResponseType calculateLoanResponse(LoanRequest request) {
        switch (request.getType()) {
            case OOO:
                if (request.getAmount() > 10_000 && request.getMonths() < 12) {
                    return ResponseType.APPROVED;
                }
            case PERSON:
                if (request.getAmount() <= 10_000 && request.getMonths() <= 12) {
                    return ResponseType.APPROVED;
                }
            default:
                return ResponseType.DENIED;
        }
    }

    @Override
    public ResponseType getApplicationStatus(UUID requestId) throws NoSuchElementException {
        LoanCalcRow row = repo.getRowById(requestId);
        return row.getStatus();
    }

    @Override
    public ResponseType setApplicationStatus(UUID requestId, ResponseType response) {
        LoanCalcRow row = repo.getRowById(requestId);
        row.setStatus(response);
        return row.getStatus();
    }
}

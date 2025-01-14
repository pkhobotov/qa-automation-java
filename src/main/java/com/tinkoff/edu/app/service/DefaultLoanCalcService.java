package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.GetApplicationException;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultLoanCalcService implements LoanCalcService {
    protected LoanCalcRepository repo;

    public DefaultLoanCalcService(LoanCalcRepository repo) {
        this.repo = repo;
    }

    public UUID createRequest(LoanRequest request) {
        LoanApplication application = new LoanApplication(request);
        application.setResponse(this.calculateLoanResponse(request));
        return this.repo.saveNew(application);
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
    public ResponseType getApplicationStatus(UUID requestId) {
        try {
            return repo.getItemById(requestId).getResponse();
        } catch (NullPointerException e) {
            throw new GetApplicationException("No application for this ID",
                    e);
        }
    }

    @Override
    public ResponseType setApplicationStatus(UUID requestId, ResponseType response) {
        try {
            repo.setAppResponse(requestId, response);
        } catch (NullPointerException e) {
            throw new GetApplicationException("No application for this ID",
                    e);
        }
        return repo.getItemById(requestId).getResponse();
    }

    @Override
    public List<LoanApplication> getApplicationsByRequesterType(Requester requester) {
        Map<UUID, LoanApplication> applications = repo.getApplications();
        return applications.values().stream()
                .filter((application) -> application.getRequestType().equals(requester))
                .collect(Collectors.toList());
    }

    @Override
    public double sumLoanAmountByRequesterType(Requester requester) {
        List<LoanApplication> applications = getApplicationsByRequesterType(requester);
        return applications
                .stream()
                .map(LoanApplication::getRequestAmount)
                .reduce(0.0,
                        Double::sum);
    }
}

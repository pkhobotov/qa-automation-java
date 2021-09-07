package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.exceptions.GetApplicationException;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.util.Map;
import java.util.Set;
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
        return this.repo.save(application);
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
        LoanApplication application;
        application = repo.getItemById(requestId);
        try {
            application.setResponse(response);
        } catch (NullPointerException e) {
            throw new GetApplicationException("No application for this ID",
                                              e);
        }
        return application.getResponse();
    }

    @Override
    public Set <LoanApplication> getApplicationsByRequesterType(Requester requester) {
        Map <UUID, LoanApplication> applications = repo.getApplications();
        return applications.values().stream()
                .filter((application) -> application.getRequest().getType().equals(requester))
                .collect(Collectors.toSet());
    }

    @Override
    public double sumLoanAmountByRequesterType(Requester requester) {
        Set <LoanApplication> applications = getApplicationsByRequesterType(requester);
        return applications
                .stream()
                .map(a -> a.getRequest().getAmount())
                .reduce(0.0,
                        Double::sum);
    }
}

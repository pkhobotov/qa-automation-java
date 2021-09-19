package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.Map;
import java.util.UUID;

public interface LoanCalcRepository {
    UUID saveNew(LoanApplication application);

    LoanApplication getItemById(UUID requestId);

    Map<UUID, LoanApplication> getApplications();

    void setAppResponse(UUID requestId, ResponseType response);
}

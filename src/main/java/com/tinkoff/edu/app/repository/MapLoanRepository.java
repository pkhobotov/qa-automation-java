package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.ResponseType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapLoanRepository implements LoanCalcRepository {
    private final Map<UUID, LoanApplication> mapRepository = new HashMap<>();

    @Override
    public UUID saveNew(LoanApplication application) {
        UUID requestId = UUID.randomUUID();
        application.setRequestId(requestId);
        this.mapRepository.put(requestId,
                application);
        return requestId;
    }

    @Override
    public LoanApplication getItemById(UUID requestId) {
        return this.mapRepository.get(requestId);
    }

    @Override
    public Map<UUID, LoanApplication> getApplications() {
        return Collections.unmodifiableMap(mapRepository);
    }

    @Override
    public void setAppResponse(UUID requestId, ResponseType response) {

    }
}

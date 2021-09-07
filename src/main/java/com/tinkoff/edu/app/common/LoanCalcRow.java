package com.tinkoff.edu.app.common;

import java.util.UUID;

public class LoanCalcRow {
    private final UUID requestId;
    private final LoanApplication application;

    public LoanCalcRow(UUID requestId, LoanApplication application) {
        this.requestId = requestId;
        this.application = application;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public LoanApplication getItem() {
        return application;
    }
}

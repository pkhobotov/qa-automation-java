package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;

import java.util.UUID;

public interface LoanCalcRepository {
    UUID save(LoanApplication application);

    LoanApplication getItemById(UUID requestId);
}

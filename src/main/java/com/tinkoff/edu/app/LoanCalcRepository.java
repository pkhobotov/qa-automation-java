package com.tinkoff.edu.app;

import java.util.UUID;

public interface LoanCalcRepository {
    UUID save(LoanRequest request);
}

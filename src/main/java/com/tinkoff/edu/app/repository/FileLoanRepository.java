package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

public class FileLoanRepository implements LoanCalcRepository {

    @Override
    public UUID save(LoanApplication application) {
        var requestId = UUID.randomUUID();
        application.setRequestId(requestId);
        Path path = Path.of("target", "LoanRepository.csv");


        try {
            Files.writeString(path, application.toString(), APPEND, CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return requestId;
    }

    @Override
    public LoanApplication getItemById(UUID requestId) {
        return null;
    }

    @Override
    public Map<UUID, LoanApplication> getApplications() {
        return null;
    }
}

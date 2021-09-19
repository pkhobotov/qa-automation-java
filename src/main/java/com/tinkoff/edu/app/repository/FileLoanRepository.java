package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

public class FileLoanRepository implements LoanCalcRepository {
    private final Path path;
    String header = "requestId, requester, amount, months, FIO";

    public FileLoanRepository() {
        this(Path.of("target", "LoanRepository.csv"));
    }

    public FileLoanRepository(Path path) {
        this.path = path;
        if (!Files.exists(this.path)) {
            try {
                Files.writeString(path, header + "\n", CREATE, WRITE);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл", e);
            }
        }
    }

    @Override
    public UUID save(LoanApplication application) {
        var requestId = UUID.randomUUID();
        application.setRequestId(requestId);

        try {
            Files.writeString(path, application.toString() + '\n', APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить строку в файл", e);
        }

        return requestId;
    }

    @Override
    public LoanApplication getItemById(UUID requestId) {
        String appString;
        try {
            var stream = Files.lines(path);
            return (LoanApplication) stream.filter(l->l.contains(requestId.toString()))
                    .map(LoanApplication::fromString);

        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать строку из файла", e);
        }
    }

    @Override
    public Map<UUID, LoanApplication> getApplications() {
        return null;
    }
}

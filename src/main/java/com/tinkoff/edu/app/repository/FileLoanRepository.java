package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.ResponseType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
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
        createFile(path);
    }

    private void createFile(Path path) {
        if (!Files.exists(this.path)) {
            try {
                Files.writeString(path, header + "\n", CREATE, WRITE);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл", e);
            }
        }
    }

    @Override
    public UUID saveNew(LoanApplication application) {
        var requestId = UUID.randomUUID();
        application.setRequestId(requestId);
        save(application);
        return requestId;
    }

    private void save(LoanApplication application) {
        try {
            Files.writeString(path, application.toString() + '\n', APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить строку в файл", e);
        }
    }

    @Override
    public LoanApplication getItemById(UUID requestId) {
        var apps = getApplications();
        return apps.get(requestId);
    }

    @Override
    public Map<UUID, LoanApplication> getApplications() {
        var applicationsMap = new HashMap<UUID, LoanApplication>();
        try {
            var lines = Files.readAllLines(path);
            for (var l = 1; l < lines.size(); l++) {
                var application = LoanApplication.fromString(lines.get(l));
                applicationsMap.put(application.getRequestId(), application);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать строку из файла", e);
        }
        return applicationsMap;
    }

    private void delete(UUID requestId) {
        var applications = getApplications();
        applications.remove(requestId);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось удалить файл", e);
        }
        createFile(path);
        applications.values().forEach(this::save);
    }

    @Override
    public void setAppResponse(UUID requestId, ResponseType response) {
        var apps = getApplications();
        var application = apps.get(requestId);
        delete(requestId);
        application.setResponse(response);
        save(application);
    }
}

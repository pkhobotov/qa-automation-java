package com.tinkoff.edu.app.common;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoanApplication {
    private final LoanRequest request;
    private UUID requestId;
    private ResponseType response;

    public LoanApplication(LoanRequest request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplication that = (LoanApplication) o;
        return request.equals(that.request) && requestId.equals(that.requestId) && response == that.response;
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, requestId, response);
    }

    public LoanRequest getRequest() {
        return request;
    }

    public ResponseType getResponse() {
        return response;
    }

    public void setResponse(ResponseType response) {
        this.response = response;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String toString() {
        var values = List.of(this.requestId.toString(), this.request.toString());
        return String.join(", ", values);
    }
}

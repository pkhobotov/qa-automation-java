package com.tinkoff.edu.app.common;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoanApplication {
    private final Requester requestType;
    private final double requestAmount;
    private final int requestMonths;
    private final String requestFio;
    private UUID requestId;
    private ResponseType response;

    public LoanApplication(LoanRequest request) {
        this.requestAmount = request.getAmount();
        this.requestFio = request.getFio();
        this.requestMonths = request.getMonths();
        this.requestType = request.getType();
    }

    public LoanApplication(Requester requestType, double requestAmount, int requestMonths, String requestFio, UUID requestId, ResponseType response) {
        this.requestType = requestType;
        this.requestAmount = requestAmount;
        this.requestMonths = requestMonths;
        this.requestFio = requestFio;
        this.requestId = requestId;
        this.response = response;
    }

    public static LoanApplication fromString(String str) {
        var arr = str.split(", ");
        var requestId = UUID.fromString(arr[0]);
        var requester = Requester.valueOf(arr[1]);
        var amount = Double.parseDouble(arr[2]);
        var months = Integer.parseInt(arr[3]);
        var response = ResponseType.valueOf(arr[4]);
        var fio = arr[5];

        return new LoanApplication(requester, amount, months, fio, requestId, response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplication that = (LoanApplication) o;
        return Double.compare(that.requestAmount, requestAmount) == 0
                && requestMonths == that.requestMonths
                && requestType == that.requestType
                && Objects.equals(requestFio, that.requestFio)
                && Objects.equals(requestId, that.requestId)
                && response == that.response;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestType, requestAmount, requestMonths, requestFio, requestId, response);
    }

    public Requester getRequestType() {
        return requestType;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public int getRequestMonths() {
        return requestMonths;
    }

    public String getRequestFio() {
        return requestFio;
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
        return Stream.of(
                        this.requestId,
                        this.requestType,
                        this.requestAmount,
                        this.requestMonths,
                        this.response,
                        this.requestFio
                ).map(Objects::toString)
                .collect(Collectors.joining(", "));
    }
}

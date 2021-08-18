package com.tinkoff.edu.app;

/**
 * Class, Type -> objects, instances
 */
public class LoanRequest {
    public final int requestId;
    private final int months;
    private final int amount;
    private final LoanType type;
    private LoanResponse.ResponseType responseType;

    public LoanRequest(int months , int amount , LoanType type , int requestId) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.requestId = requestId;
    }

    public LoanResponse.ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(LoanResponse.ResponseType responseType) {
        this.responseType = responseType;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }

    public String getResponse() {
        return "This request is " + this.responseType;
    }

    public String toString() {
        return this.type + " requests : {$" + this.getAmount() + " for " + this.getMonths() + " months}";
    }
}

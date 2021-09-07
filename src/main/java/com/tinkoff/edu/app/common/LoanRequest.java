package com.tinkoff.edu.app.common;

/**
 * Class, Type -> objects, instances
 */
public class LoanRequest {
    private final int months;
    private final double amount;
    private final Requester type;
    private final String fio;

    public LoanRequest(int months, double amount, Requester type, String fio) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public Requester getType() {
        return type;
    }

    public int getMonths() {
        return months;
    }

    public double getAmount() {
        return amount;
    }

    public String toString() {
        return this.type + " requests : {$" + this.getAmount() + " for " + this.getMonths() + " months}";
    }
}

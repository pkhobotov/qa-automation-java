package com.tinkoff.edu.app.common;

/**
 * Class, Type -> objects, instances
 */
public class LoanRequest {
    private final int months;
    private final double amount;
    private final LoanType type;
    private final String fio;

    public String getFio() {
        return fio;
    }

    public LoanRequest(int months, double amount, LoanType type, String fio) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.fio = fio;
    }

    public LoanType getType() {
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

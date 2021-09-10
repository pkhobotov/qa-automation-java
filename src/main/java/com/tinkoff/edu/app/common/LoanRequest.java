package com.tinkoff.edu.app.common;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRequest that = (LoanRequest) o;
        return months == that.months && Double.compare(that.amount, amount) == 0 && type == that.type && fio.equals(that.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(months, amount, type, fio);
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

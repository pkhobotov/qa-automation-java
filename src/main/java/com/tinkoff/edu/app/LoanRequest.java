package com.tinkoff.edu.app;

/**
 * Class, Type -> objects, instances
 */
public class LoanRequest {
    private final int months;
    private final int amount;
    private final LoanType type;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public LoanRequest(int months, int amount, LoanType type, String firstName, String middleName, String lastName) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public LoanType getType() {
        return type;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        return this.type + " requests : {$" + this.getAmount() + " for " + this.getMonths() + " months}";
    }
}

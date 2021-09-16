package com.tinkoff.edu;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;


public class RequestBuilder {
    private double amount = 8000.0;
    private int months = 8;
    private Requester type = Requester.PERSON;
    private String fio = "Иванов Иван Иванович";
    public static RequestBuilder RequestBuilder = new RequestBuilder();



    public LoanRequest build() {
        return new LoanRequest(this.months, this.amount, this.type, this.fio);
    }

    public RequestBuilder amount(double amount) {
        this.amount = amount;
        return this;
    }

    public RequestBuilder requester(Requester type) {
        this.type = type;
        return this;
    }

    public RequestBuilder fio(String fio) {
        this.fio = fio;
        return this;
    }

    public RequestBuilder months(int months) {
        this.months = months;
        return this;
    }
}

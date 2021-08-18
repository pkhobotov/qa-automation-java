package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.LoanType;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanRequest request = new LoanRequest(10, 1000, LoanType.IP);
        LoanCalcController calcController = new LoanCalcController();
        LoanResponse response = calcController.createRequest(request);

        System.out.println(response);

    }
}

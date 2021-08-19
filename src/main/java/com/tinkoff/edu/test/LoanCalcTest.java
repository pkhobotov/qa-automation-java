package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanRequest request = new LoanRequest(10,
                                              1000,
                                              LoanType.IP);
        LoanCalcController calcController = new LoanCalcController();
        LoanResponse response = calcController.createRequest(request);
        LoanCalcLogger.log(response);
    }
}

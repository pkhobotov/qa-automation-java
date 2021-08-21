package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanRequest request = new LoanRequest(10,
                                              1000,
                                              LoanType.OOO);
        LoanCalcController loanCalcController = new LoanCalcController(new StaticVariableLoanCalcRepository());
        LoanResponse response = loanCalcController.createRequest(request);
        LoanCalcLogger.log(response);
    }
}

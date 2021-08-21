package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

public class LoanCalcTest {
    private static final StaticVariableLoanCalcRepository repo = new StaticVariableLoanCalcRepository();

    public static void main(String... args) {
        LoanRequest request = new LoanRequest(10,
                                              1000,
                                              LoanType.OOO);
        LoanCalcControllerInterface loanCalcController = new LoanCalcController(repo);
        LoanResponse response = loanCalcController.createRequest(request);
        LoanCalcLogger.log(response);
    }
}

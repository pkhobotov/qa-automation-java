package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanCalcRepository repo = new StaticVariableLoanCalcRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        LoanCalcController calcController = new DefaultLoanCalcController(calcService);
        LoanRequest request = new LoanRequest(10,
                                              1000,
                                              LoanType.IP);
        LoanResponse response = calcController.createRequest(request);
        LoanCalcLogger.log(response);
    }
}

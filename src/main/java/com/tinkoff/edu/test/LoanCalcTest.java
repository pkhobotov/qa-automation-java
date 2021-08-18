package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanCalcLogger;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanType;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanCalcController calcController = new LoanCalcController();
        LoanRequest request = calcController.createRequest(10 ,
                                                           1000 ,
                                                           LoanType.IP);
        LoanCalcLogger.log(request.requestId + " must be 1");
        LoanCalcLogger.log(request.toString());
        LoanCalcLogger.log(request.getResponse());
    }
}

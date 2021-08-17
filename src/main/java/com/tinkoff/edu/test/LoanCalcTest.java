package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanRequest;
import com.tinkoff.edu.app.LoanResponse;
import com.tinkoff.edu.app.LoanType;

public class LoanCalcTest {
    public static void main(String... args) {
        LoanCalcController calcController = new LoanCalcController();
        LoanResponse response = new LoanResponse();
        LoanRequest loanRequest = new LoanRequest(10 ,
                                                  1000 ,
                                                  LoanType.IP);
        int requestId = calcController.createRequest(loanRequest);

        System.out.println(loanRequest);
        System.out.println(response.createResponse(loanRequest));
        System.out.println(requestId + " must be 1");
    }
}

package com.tinkoff.edu.app;

import static com.tinkoff.edu.app.LoanCalcLogger.log;

public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     */
    public LoanRequest createRequest(int months , int amount , LoanType loanType) {
        //param validation
        //log
        LoanCalcRepository calcRepository = new LoanCalcRepository();
        int requestId = calcRepository.save();
        LoanRequest loanRequest = new LoanRequest(months ,
                                                  amount ,
                                                  loanType ,
                                                  requestId);
        LoanCalcService calcService = new LoanCalcService();
        log(loanRequest.toString());
        calcService.calculateLoan(loanRequest);
        return loanRequest;
    }
}

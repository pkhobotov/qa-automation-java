package com.tinkoff.edu.app;

import static com.tinkoff.edu.app.LoanCalcLogger.log;

public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     *
     * @param request
     */
    public int createRequest(LoanRequest request) {
        LoanCalcService calcService = new LoanCalcService();
        //param validation
        //log
        log(request);
        return calcService.createRequest(request);
    }
}

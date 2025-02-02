package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

public class IpNotFriendlyLoanCalcService extends DefaultLoanCalcService {
    public IpNotFriendlyLoanCalcService(LoanCalcRepository repo) {
        super(repo);
    }

    @Override
    public ResponseType calculateLoanResponse(LoanRequest request) {
        if (request.getType().equals(Requester.IP)) return ResponseType.DENIED;
        return super.calculateLoanResponse(request);
    }
}

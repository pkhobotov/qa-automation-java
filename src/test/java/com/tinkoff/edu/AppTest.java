package com.tinkoff.edu;

import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private LoanCalcService calcService;
    private LoanCalcController calcController;
    private LoanRequest request;
    private LoanCalcRepository repo;

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        repo = new StaticVariableLoanCalcRepository();
        request = new LoanRequest(10,
                                  1000,
                                  LoanType.IP);
        calcService = new IpNotFriendlyLoanCalcService(repo);
        calcController = new DefaultLoanCalcController(calcService);
        //endregion
    }

    @Test
    @DisplayName("Should get 1 when first request")
    public void shouldGet1WhenFirstRequest() {
        //region Act | When
        LoanResponse response = calcController.createRequest(request);
        //endregion
        //region Assert | Then
        assertEquals(1, response.getRequestId());
        //endregion
    }
}

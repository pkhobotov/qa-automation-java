package com.tinkoff.edu;

import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    private LoanCalcController calcController;
    private LoanRequest request;

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new StaticVariableLoanCalcRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        calcController = new DefaultLoanCalcController(calcService);
        request = new LoanRequest(10,
                                  1000,
                                  LoanType.IP);
        //endregion
    }

    @Test
    @DisplayName("Should get 1 when first request")
    public void shouldGet1WhenFirstRequest() {
        //region Act | When
        LoanResponse response = calcController.createRequest(request);
        //endregion
        //region Assert | Then
        assertEquals(1,
                     response.getRequestId());
        //endregion
    }

    @Test
    @DisplayName("Should get +1 incremented after each call")
    public void shouldGetPlus1AfterEachCall() {
        //region Act | When
        int firstRequestId = calcController.createRequest(request).getRequestId();
        int secondRequestId = calcController.createRequest(request).getRequestId();
        //endregion
        //region Assert | Then
        assertEquals(firstRequestId + 1,
                     secondRequestId);
        //endregion
    }
}

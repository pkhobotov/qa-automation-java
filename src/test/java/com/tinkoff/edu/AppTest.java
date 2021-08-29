package com.tinkoff.edu;

import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private LoanCalcController sut;
    private LoanRequest request;
    private LoanResponse response;

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new VariableLoanCalcRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        request = new LoanRequest(10,
                                  1000,
                                  LoanType.IP);
        //endregion
    }

    @Test
    @DisplayName("Should get 1 when first request")
    public void shouldGet1WhenFirstRequest() {
        //region Act | When
        response = sut.createRequest(request);
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
        int firstRequestId = sut.createRequest(request).getRequestId();
        int secondRequestId = sut.createRequest(request).getRequestId();
        //endregion
        //region Assert | Then
        assertEquals(firstRequestId + 1,
                     secondRequestId);
        //endregion
    }

    @Test
    @DisplayName("Request denied if requester is IP")
    public void shouldGetDeniedIdRequestedFromIp() {
        LoanType requester = LoanType.IP;
        request = new LoanRequest(10,
                                  1000,
                                  requester);
        LoanResponse response = sut.createRequest(request);
        assertEquals(response.getResponseType(),
                     ResponseType.DENIED);
    }

    @Test
    @DisplayName("Throws error on null request")
    public void shouldGetErrorWhenApplyNullRequest() {
        request = null;
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative amount")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        request = new LoanRequest(2000,
                                  -2,
                                  LoanType.OOO);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative months")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(-3,
                                  2000,
                                  LoanType.OOO);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }
}

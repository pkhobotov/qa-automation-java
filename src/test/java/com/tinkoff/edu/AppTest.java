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

    @Test
    @DisplayName("Request denied if requester is IP")
    public void shouldGetDeniedIdRequestedFromIp() {
        LoanType requester = LoanType.IP;
        request = new LoanRequest(10,
                                  1000,
                                  requester);
        LoanResponse response = sut.createRequest(request);
        assertEquals(ResponseType.DENIED, response.getResponseType());
    }

    @Test
    @DisplayName("Request denied if requested too much money")
    public void shouldGetDeniedWhenRequestsTooMuchMoney(){
        request = new LoanRequest(4, 20000, LoanType.PERSON);
        response = sut.createRequest(request);
        assertEquals(ResponseType.DENIED, response.getResponseType());
    }

    @Test
    @DisplayName("Request denied if requested for too many months")
    public void shouldGetDeniedWhenRequestsForTooManyMonths(){
        request = new LoanRequest(16, 8000, LoanType.PERSON);
        response = sut.createRequest(request);
        assertEquals(ResponseType.DENIED, response.getResponseType());
    }

    @Test
    @DisplayName("Request approved if requested not too much money for not too many months")
    public void shouldGetApprovedWhenRequestedNotTooMuchForNotTooLong(){
        request = new LoanRequest(4, 8000, LoanType.OOO);
        response = sut.createRequest(request);
        assertEquals(ResponseType.APPROVED, response.getResponseType());
    }
}

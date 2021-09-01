package com.tinkoff.edu;

import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private static final String defaultFirstName = "Гриша";
    private static final String defaultLastName = "Петров";
    private static final String defaultMiddleName = "Алексеевич";
    private LoanCalcController sut;
    private LoanRequest request;

    public static Stream <LoanRequest> buildApprovalRequest() {
        return Stream.of(
                new LoanRequest(4,
                                12000,
                                LoanType.OOO,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName),
                new LoanRequest(4,
                                8000,
                                LoanType.OOO,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName)
        );
    }

    public static Stream <LoanRequest> buildDeniableRequest() {
        return Stream.of(
                new LoanRequest(14,
                                12000,
                                LoanType.OOO,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName),
                new LoanRequest(16,
                                8000,
                                LoanType.PERSON,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName),
                new LoanRequest(4,
                                20000,
                                LoanType.PERSON,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName),
                new LoanRequest(10,
                                1000,
                                LoanType.IP,
                                defaultFirstName,
                                defaultMiddleName,
                                defaultLastName)
        );
    }

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new ArrayLoanCalcRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        //endregion
    }
//    private void buildDefaultRequest() {
//        request = new LoanRequest(10,
//                                  1000,
//                                  LoanType.IP,
//                                  defaultFirstName,
//                                  defaultMiddleName,
//                                  defaultLastName);
//    }

    //region requestId tests
    //    @Test
    //    @DisplayName("Should get 1 when first request")
    //    public void shouldGet1WhenFirstRequest() {
    //        //region Act | When
    //        buildDefaultRequest();
    //        LoanResponse response = sut.createRequest(request);
    //        //endregion
    //        //region Assert | Then
    //        assertEquals(1,
    //                     response.getRequestId());
    //        //endregion
    //    }
    //
    //    @Test
    //    @DisplayName("Should get +1 incremented after each call")
    //    public void shouldGetPlus1AfterEachCall() {
    //        //region Act | When
    //        buildDefaultRequest();
    //        UUID firstRequestId = sut.createRequest(request).getRequestId();
    //        UUID secondRequestId = sut.createRequest(request).getRequestId();
    //        //endregion
    //        //region Assert | Then
    //        assertEquals(firstRequestId + 1,
    //                     secondRequestId);
    //        //endregion
    //    }
//endregion
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
                                  LoanType.OOO,
                                  defaultFirstName,
                                  defaultMiddleName,
                                  defaultLastName);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative months")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(-3,
                                  2000,
                                  LoanType.OOO,
                                  defaultFirstName,
                                  defaultMiddleName,
                                  defaultLastName);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @ParameterizedTest
    @MethodSource("buildApprovalRequest")
    public void getApproved(LoanRequest request) {
        LoanResponse response = sut.createRequest(request);
        assertEquals(ResponseType.APPROVED,
                     response.getResponseType());
    }

    @ParameterizedTest
    @MethodSource("buildDeniableRequest")
    public void getDenied(LoanRequest request) {
        LoanResponse response = sut.createRequest(request);
        assertEquals(ResponseType.DENIED,
                     response.getResponseType());
    }
}

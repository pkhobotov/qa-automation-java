package com.tinkoff.edu;

import com.tinkoff.edu.app.common.LoanApplication;
import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.LoanType;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exceptions.*;
import com.tinkoff.edu.app.repository.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private static final String defaultFio = "Петров Гриша Сергеевич";
    private LoanCalcController sut;
    private LoanRequest request;

    public static Stream <LoanRequest> buildApprovalRequest() {
        return Stream.of(
                new LoanRequest(4,
                                12000,
                                LoanType.OOO,
                                defaultFio),
                new LoanRequest(4,
                                8000,
                                LoanType.OOO,
                                defaultFio)
        );
    }

    public static Stream <LoanRequest> buildDeniableRequest() {
        return Stream.of(
                new LoanRequest(14,
                                12000,
                                LoanType.OOO,
                                defaultFio),
                new LoanRequest(16,
                                8000,
                                LoanType.PERSON,
                                defaultFio),
                new LoanRequest(4,
                                20000,
                                LoanType.PERSON,
                                defaultFio),
                new LoanRequest(10,
                                1000,
                                LoanType.IP,
                                defaultFio)
        );
    }

    private LoanRequest buildDefaultRequest() {
        return new LoanRequest(2,
                               8000,
                               LoanType.OOO,
                               defaultFio);
    }

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new ArrayLoanCalcRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
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
                                  LoanType.OOO,
                                  defaultFio);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative months")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(-3,
                                  2000,
                                  LoanType.OOO,
                                  defaultFio);
        assertThrows(IllegalArgumentException.class,
                     () -> sut.createRequest(request));
    }

    @DisplayName("request should be APPROVED")
    @ParameterizedTest
    @MethodSource("buildApprovalRequest")
    public void getApproved(LoanRequest request) throws RequestException {
        LoanApplication response = sut.createRequest(request);
        assertEquals(ResponseType.APPROVED,
                     response.getResponse());
    }

    @DisplayName("request should be DENIED")
    @ParameterizedTest
    @MethodSource("buildDeniableRequest")
    public void getDenied(LoanRequest request) throws RequestException {
        LoanApplication response = sut.createRequest(request);
        assertEquals(ResponseType.DENIED,
                     response.getResponse());
    }

    @Test
    public void shouldReturnApplicationUUID() throws RequestException {
        request = buildDefaultRequest();
        LoanApplication application = sut.createRequest(request);
        UUID requestId = application.getRequestId();
        assertEquals(application.getResponse(),
                     sut.getApplicationStatus(requestId));
    }

    @Test
    public void shouldReturnExceptionWhenNoRequestStored() {
        assertThrows(GetApplicationException.class,
                     () -> sut.getApplicationStatus(UUID.randomUUID()));
    }

    @Test
    public void shouldReturnExceptionWhenNoRequestFound() throws RequestException {
        request = buildDefaultRequest();
        sut.createRequest(request);
        assertThrows(GetApplicationException.class,
                     () -> sut.getApplicationStatus(UUID.randomUUID()));
    }
    @Test
    public void shouldReturnExceptionWhenNoRequestFoundToSetStatus() throws RequestException {
        request = buildDefaultRequest();
        sut.createRequest(request);
        assertThrows(GetApplicationException.class,
                     () -> sut.setApplicationStatus(UUID.randomUUID(), ResponseType.DENIED));
    }

    @Test
    public void shouldSetStatusToDesired() throws RequestException {
        request = buildDefaultRequest();
        LoanApplication application = sut.createRequest(request);
        UUID requestId = application.getRequestId();
        Assumptions.assumeTrue(sut.getApplicationStatus(requestId).equals(ResponseType.APPROVED));
        assertEquals(ResponseType.DENIED,
                     sut.setApplicationStatus(requestId,
                                              ResponseType.DENIED));
    }

    @Test
    public void getErrorOnShortFIO() {
        request = new LoanRequest(4,
                                  8000,
                                  LoanType.PERSON,
                                  "Крткм");
        assertThrows(FIOLengthException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    public void getErrorOnLongFIO() {
        request = new LoanRequest(4,
                                  8000,
                                  LoanType.PERSON,
                                  "Крткм".repeat(21));
        assertThrows(FIOLengthException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    public void getErrorOnInvalidCharContainingFIO() {
        request = new LoanRequest(4,
                                  8000,
                                  LoanType.PERSON,
                                  "Крткм".repeat(3)+"/");
        assertThrows(IllegalCharacterException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    public void getErrorOnSmallerAmount() {
        request = new LoanRequest(4,
                                  0.001,
                                  LoanType.PERSON,
                                  defaultFio);
        assertThrows(IllegalRequestAmountException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    public void getErrorOnBiggerAmount() {
        request = new LoanRequest(4,
                                  1000000000,
                                  LoanType.PERSON,
                                  defaultFio);
        assertThrows(IllegalRequestAmountException.class,
                     () -> sut.createRequest(request));
    }
}

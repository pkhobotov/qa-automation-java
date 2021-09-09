package com.tinkoff.edu;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exceptions.RequestException;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.repository.MapLoanRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {
    private static final String defaultFio = "Петров Гриша Сергеевич";
    private LoanCalcController sut;
    private LoanRequest request;

    public static Stream<LoanRequest> buildApprovalRequest() {
        return Stream.of(
                new LoanRequest(4,
                        12000,
                        Requester.OOO,
                        defaultFio),
                new LoanRequest(4,
                        8000,
                        Requester.OOO,
                        defaultFio)
        );
    }

    public static Stream<LoanRequest> buildDeniableRequest() {
        return Stream.of(
                new LoanRequest(14,
                        12000,
                        Requester.OOO,
                        defaultFio),
                new LoanRequest(16,
                        8000,
                        Requester.PERSON,
                        defaultFio),
                new LoanRequest(4,
                        20000,
                        Requester.PERSON,
                        defaultFio),
                new LoanRequest(10,
                        1000,
                        Requester.IP,
                        defaultFio)
        );
    }

    private LoanRequest buildDefaultRequest() {
        return new LoanRequest(2,
                8000,
                Requester.OOO,
                defaultFio);
    }

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new MapLoanRepository();
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
                Requester.OOO,
                defaultFio);
        assertThrows(IllegalArgumentException.class,
                () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative months")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(-3,
                2000,
                Requester.OOO,
                defaultFio);
        assertThrows(IllegalArgumentException.class,
                () -> sut.createRequest(request));
    }

    @DisplayName("request should be APPROVED")
    @ParameterizedTest
    @MethodSource("buildApprovalRequest")
    public void getApproved(LoanRequest request) throws RequestException {
        UUID requestId = sut.createRequest(request);
        assertEquals(ResponseType.APPROVED,
                sut.getApplicationStatus(requestId));
    }

    @DisplayName("request should be DENIED")
    @ParameterizedTest
    @MethodSource("buildDeniableRequest")
    public void getDenied(LoanRequest request) throws RequestException {
        UUID requestId = sut.createRequest(request);
        assertEquals(ResponseType.DENIED,
                sut.getApplicationStatus(requestId));
    }

    @Test
    public void shouldReturnApplicationStatusOnUUID() throws RequestException {
        request = buildDefaultRequest();
        sut.createRequest(request);
        UUID requestId = sut.createRequest(request);
        assertEquals(ResponseType.APPROVED,
                sut.getApplicationStatus(requestId));
    }

    @Test
    public void shouldSetStatusToDesired() throws RequestException {
        request = buildDefaultRequest();
        UUID requestId = sut.createRequest(request);
        Assumptions.assumeTrue(sut.getApplicationStatus(requestId).equals(ResponseType.APPROVED));
        assertEquals(ResponseType.DENIED,
                sut.setApplicationStatus(requestId,
                        ResponseType.DENIED));
    }

    @Test
    public void shouldSumAllSameTypeRequestsAmounts() throws RequestException {
        double[] amounts = {8000.1, 20.66, 20.34};
        Requester testRequester = Requester.OOO;
        sut.createRequest(new LoanRequest(2,
                amounts[0],
                testRequester,
                defaultFio));
        sut.createRequest(new LoanRequest(5,
                amounts[1],
                testRequester,
                defaultFio));
        sut.createRequest(new LoanRequest(3,
                amounts[2],
                testRequester,
                defaultFio));
        sut.createRequest(new LoanRequest(2,
                7654,
                Requester.IP,
                defaultFio));
        sut.createRequest(new LoanRequest(2,
                556,
                Requester.PERSON,
                defaultFio));
        assertEquals(Arrays.stream(amounts).sum(),
                sut.sumLoanAmountByRequesterType(testRequester));
    }
}

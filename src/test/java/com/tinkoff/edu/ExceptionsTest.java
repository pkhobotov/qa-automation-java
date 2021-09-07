package com.tinkoff.edu;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTest {
    private static final String defaultFio = "Петров Гриша Сергеевич";
    private LoanCalcController sut;
    private LoanRequest request;

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
    public void shouldReturnExceptionWhenLoanTypeIsNull() {
        request = new LoanRequest(4,
                                  8000,
                                  null,
                                  "Крткм".repeat(5));
        assertThrows(LoanTypeException.class,
                     () -> sut.createRequest(request));
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
                     () -> sut.setApplicationStatus(UUID.randomUUID(),
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
    public void getErrorOnNullFIO() {
        request = new LoanRequest(4,
                                  8000,
                                  LoanType.PERSON,
                                  null);
        assertThrows(FIOLengthException.class,
                     () -> sut.createRequest(request));
    }

    @Test
    public void getErrorOnInvalidCharContainingFIO() {
        request = new LoanRequest(4,
                                  8000,
                                  LoanType.PERSON,
                                  "Крткм".repeat(3) + "/");
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

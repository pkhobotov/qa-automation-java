package com.tinkoff.edu;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.common.ResponseType;
import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exceptions.*;
import com.tinkoff.edu.app.repository.FileLoanRepository;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.common.RequestBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static com.tinkoff.edu.app.common.Requester.OOO;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionsTest {
    private static final String defaultFio = "Петров Гриша Сергеевич";
    private LoanCalcController sut;
    private LoanRequest request;
    private Path path = Path.of("src", "main", "resources", "LoanRepository.csv");


    private LoanRequest buildDefaultRequest() {
        return new LoanRequest(2,
                8000,
                OOO,
                defaultFio);
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        LoanCalcRepository repo = new FileLoanRepository(path);
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
    public void getErrorOnInvalidCharContainingFIO() {
        request = new LoanRequest(4,
                8000,
                Requester.PERSON,
                "Крткм".repeat(3) + "/");
        assertThrows(IllegalCharacterException.class,
                () -> sut.createRequest(request));
    }

    @NullSource
    @ParameterizedTest
    @ValueSource(strings = {"asefaeofawoefowaijefwagaeroajepwokrrwqwkpowakerkaoiwjreoiaejtoiejfefaййвйцвйцвйцваукруерукрукфукфцуфцуацlcma",
            "Крткм"})
    public void getFIOLengthException(String fio) {
        assertThrows(FIOLengthException.class,
                () -> sut.createRequest(new RequestBuilder().fio(fio).build()));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0001, 1000000000})
    public void getErrorOnBiggerAmount(double amount) {
        assertThrows(IllegalRequestAmountException.class,
                () -> sut.createRequest(new RequestBuilder().amount(amount).build()));
    }

    @Nested
    public class IOExceptions {
        @BeforeEach
        public void deleteFile() throws IOException {
            Files.deleteIfExists(path);
        }

        @Test
        public void exceptionOnWritingWithNoFile() {
            assertThrows(RuntimeException.class, () -> sut.createRequest(new RequestBuilder().build()));
        }
        @Test
        public void exceptionOnReadingWithNoFile() {
            assertThrows(RuntimeException.class, () -> sut.getApplicationsByRequesterType(OOO));
        }

    }
}

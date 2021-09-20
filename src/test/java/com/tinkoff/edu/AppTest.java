package com.tinkoff.edu;

import com.tinkoff.edu.app.common.LoanRequest;
import com.tinkoff.edu.app.common.Requester;
import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.exceptions.RequestException;
import com.tinkoff.edu.app.repository.FileLoanRepository;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.common.RequestBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.tinkoff.edu.app.common.Requester.*;
import static com.tinkoff.edu.app.common.ResponseType.APPROVED;
import static com.tinkoff.edu.app.common.ResponseType.DENIED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;

public class AppTest {
    private static final String defaultFio = "Петров Гриша Сергеевич";
    private LoanCalcController sut;
    private LoanCalcRepository repo;
    private Path path = Path.of("src", "main", "resources", "LoanRepository.csv");

    public static Stream<LoanRequest> buildApprovalRequest() {
        return Stream.of(
                new RequestBuilder().months(4).amount(12000).requester(OOO).build(),
                new RequestBuilder().months(4).amount(8000).requester(OOO).build()
        );
    }

    public static Stream<LoanRequest> buildDeniableRequest() {
        return Stream.of(
                new RequestBuilder().months(14).amount(1200).requester(OOO).build(),
                new RequestBuilder().months(14).amount(14000).requester(OOO).build(),
                new RequestBuilder().months(16).amount(8000).requester(PERSON).build(),
                new RequestBuilder().months(4).amount(20000).requester(PERSON).build(),
                new RequestBuilder().months(10).amount(1000).requester(IP).build()

        );
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }

    private LoanRequest buildDefaultRequest() {
        return new LoanRequest(2,
                8000,
                OOO,
                defaultFio);
    }

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        repo = new FileLoanRepository(path);
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        //endregion
    }

    @Test
    @DisplayName("Throws error on null request")
    public void shouldGetErrorWhenApplyNullRequest() {
        LoanRequest request = null;
        assertThrows(IllegalArgumentException.class,
                () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative amount")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        var request = new LoanRequest(2000,
                -2,
                OOO,
                defaultFio);
        assertThrows(IllegalArgumentException.class,
                () -> sut.createRequest(request));
    }

    @Test
    @DisplayName("Throws error on negative months")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        var request = new LoanRequest(-3,
                2000,
                OOO,
                defaultFio);
        assertThrows(IllegalArgumentException.class,
                () -> sut.createRequest(request));
    }

    @DisplayName("request should be APPROVED")
    @ParameterizedTest
    @MethodSource("buildApprovalRequest")
    public void getApproved(LoanRequest request) throws RequestException {
        UUID requestId = sut.createRequest(request);
        assertEquals(APPROVED,
                sut.getApplicationStatus(requestId));
    }

    @DisplayName("request should be DENIED")
    @ParameterizedTest
    @MethodSource("buildDeniableRequest")
    public void getDenied(LoanRequest request) throws RequestException {
        UUID requestId = sut.createRequest(request);
        assertEquals(DENIED,
                sut.getApplicationStatus(requestId));
    }

    @Test
    public void shouldReturnApplicationStatusOnUUID() throws RequestException {
        var request = buildDefaultRequest();
        sut.createRequest(request);
        UUID requestId = sut.createRequest(request);
        assertEquals(APPROVED,
                sut.getApplicationStatus(requestId));
    }

    @Test
    public void shouldSetStatusToDesired() throws RequestException {
        var request = buildDefaultRequest();
        UUID requestId = sut.createRequest(request);
        Assumptions.assumeTrue(sut.getApplicationStatus(requestId).equals(APPROVED));
        assertEquals(DENIED,
                sut.setApplicationStatus(requestId, DENIED));
    }

    @Test
    public void shouldSumAllSameTypeRequestsAmounts() throws RequestException {
        double[] amounts = {8000.1, 20.66, 20.34};
        var testRequester = OOO;
        Stream.of(
                new RequestBuilder().amount(amounts[0]).requester(testRequester).build(),
                new RequestBuilder().amount(amounts[1]).requester(testRequester).build(),
                new RequestBuilder().amount(amounts[2]).requester(testRequester).build(),
                new RequestBuilder().amount(7654).requester(IP).build(),
                new RequestBuilder().amount(556).requester(PERSON).build()
        ).forEach(sut::createRequest);
        assertEquals(Arrays.stream(amounts).sum(),
                sut.sumLoanAmountByRequesterType(testRequester));
    }

    @Test
    public void shouldReturnAllSameRequesterTypeApplications() throws RequestException {
        var requester = OOO;
        IntStream.rangeClosed(0, 8).forEach((i) -> sut.createRequest(new LoanRequest(
                new Random().nextInt(12) + 1,
                (new Random().nextDouble() + 0.1) * 1000,
                Requester.values()[i % Requester.values().length],
                defaultFio)));
        var expectedApps = repo.getApplications().values().stream()
                .filter(app -> app.getRequestType().equals(requester))
                .collect(Collectors.toList());
        var actualApps = sut.getApplicationsByRequesterType(requester);

        MatcherAssert.assertThat(actualApps, Matchers.equalTo(expectedApps));
    }

    @Test
    public void noExceptionIfFileExistBeforeCreation(){
        this.repo = new FileLoanRepository();
        this.repo = new FileLoanRepository(path);
        sut.createRequest(new RequestBuilder().build());

    }
}

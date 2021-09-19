package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.repository.FileLoanRepository;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.common.RequestBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static com.tinkoff.edu.app.common.Requester.IP;

public class FileTest {
    private LoanCalcController sut;
    private LoanCalcRepository repo;

    private Path path = Path.of("src", "main", "resources", "LoanRepository.csv");

    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        repo = new FileLoanRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        //endregion
    }

    @Test
    public void fileTest() throws IOException {

        Stream.of(new RequestBuilder().requester(IP).build(),
                  new RequestBuilder().build(),
                  new RequestBuilder().build(),
                  new RequestBuilder().build())
                .forEach(sut::createRequest);
        Files.lines(path).forEach(System.out::println);
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }


}

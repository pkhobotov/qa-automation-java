package com.tinkoff.edu;

import com.tinkoff.edu.app.controller.DefaultLoanCalcController;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.repository.FileLoanRepository;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.service.IpNotFriendlyLoanCalcService;
import com.tinkoff.edu.app.service.LoanCalcService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static com.tinkoff.edu.RequestBuilder.RequestBuilder;
import static com.tinkoff.edu.app.common.Requester.*;
import static java.nio.file.StandardOpenOption.*;

public class FileTest {
    private LoanCalcController sut;
    private LoanCalcRepository repo;

//    @BeforeEach
    public void init() {
        //region Fixture | Arrange | Given
        repo = new FileLoanRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        //endregion
    }

    @Test
    public void fileTest() throws IOException {
        //region Fixture | Arrange | Given
        repo = new FileLoanRepository();
        LoanCalcService calcService = new IpNotFriendlyLoanCalcService(repo);
        sut = new DefaultLoanCalcController(calcService);
        //endregion
        sut.createRequest(RequestBuilder.requester(IP).build());
        sut.createRequest(RequestBuilder.build());
        sut.createRequest(RequestBuilder.build());
        sut.createRequest(RequestBuilder.build());
        Path path = Path.of("target", "LoanRepository.csv");
        System.out.println(Files.newOutputStream(path, READ));
    }


}

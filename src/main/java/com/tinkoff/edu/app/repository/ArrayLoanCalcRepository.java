//package com.tinkoff.edu.app.repository;
//
//import com.tinkoff.edu.app.common.LoanApplication;
//import com.tinkoff.edu.app.common.LoanCalcRow;
//import com.tinkoff.edu.app.common.Requester;
//
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Stream;
//
//public class ArrayLoanCalcRepository implements LoanCalcRepository {
//    private final LoanCalcRow[] arrayRepository = new LoanCalcRow[100000];
//    private int position;
//
//    @Override
//    public UUID save(LoanApplication application) {
//        UUID requestId = UUID.randomUUID();
//        LoanCalcRow row = new LoanCalcRow(requestId,
//                                          application);
//        try {
//            arrayRepository[position++] = row;
//        } catch (ArrayIndexOutOfBoundsException e) {
//            throw new ArrayIndexOutOfBoundsException("Repository is Full");
//        }
//        return requestId;
//    }
//
//    public LoanApplication getItemById(UUID requestId) throws NoSuchElementException {
//        for (int i = 0; i < position; i++) {
//            if (arrayRepository[i].getRequestId().equals(requestId)) {
//                return arrayRepository[i].getItem();
//            }
//        }
//        throw new NoSuchElementException("No element with given ID");
//    }
//
//    @Override
//    public Map <UUID, LoanApplication> getApplications() {
//        return null;
//    }
//}

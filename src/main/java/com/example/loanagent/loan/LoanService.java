package com.example.loanagent.loan;

import com.example.loanagent.exception.LoanNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class LoanService {
    private final Map<Long, Loan> loans = Map.of(

            1001L,
            new Loan(
                    1001L,
                    5001L,
                    "DISB_REVIEW",
                    "INCOME_VERIFICATION"
            ),

            1002L,
            new Loan(
                    1002L,
                    5002L,
                    "SANCTIONED",
                    "DISBURSEMENT_PENDING"
            ),

            1003L,
            new Loan(
                    1003L,
                    5003L,
                    "FULL_DISB",
                    "NONE"
            )
    );
    // this method will be invoked by the spring AI tool to fetch loan details based on loan ID.
    // It will log the process and throw an exception if the loan is not found.
    public Loan getLoan(Long loanId) {
        log.info("Retrieving loan details for loanId: {}", loanId);
        Loan loan = loans.get(loanId);
        if (loan == null) {
            log.error("Loan not found for loanId: {}", loanId);
            throw new LoanNotFoundException(loanId);
        }
        log.info("Loan found for loanId: {}", loanId);
        return loan;
    }
}
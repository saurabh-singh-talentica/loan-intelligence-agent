package com.example.loanagent.income;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;

@Slf4j
@Service
public class IncomeService {

    private final Map<Long, IncomeDetails> incomeDetails = Map.of(

            1001L,
            new IncomeDetails(
                    1001L,
                    5001L,
                    75000.0,
                    "FAILED",
                    "BANK_STATEMENT_MISMATCH"
            ),

            1002L,
            new IncomeDetails(
                    1002L,
                    5002L,
                    90000.0,
                    "VERIFIED",
                    null
            )
    );

    public IncomeDetails getIncomeDetails(Long loanId) {
        log.info("Retrieving income details for loanId: {}", loanId);
        IncomeDetails income = incomeDetails.get(loanId);
        if (income == null) {
            log.error("Income details not found for loan: {}", loanId);
            throw new IllegalArgumentException(
                    "Income details not found for loan: " + loanId
            );
        }
        log.info("Income details found for loanId: {}", loanId);
        return income;
    }
}

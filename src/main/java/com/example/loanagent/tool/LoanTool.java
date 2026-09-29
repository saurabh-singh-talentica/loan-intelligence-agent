package com.example.loanagent.tool;

import com.example.loanagent.exception.LoanNotFoundException;
import com.example.loanagent.loan.Loan;
import com.example.loanagent.loan.LoanService;
import com.example.loanagent.tool.dto.LoanToolResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

//Spring AI Tool to fetch loan details based on loan ID.
// This tool can be invoked by the AI agent when a user asks about a specific loan's status, customer ID, or pending process.
//Spring AI will invoke the getLoanDetails method when the user asks about a specific loan's status, customer ID, or pending process.
// The method takes a loan ID as input and returns the corresponding Loan object.
@Component
@Slf4j
public class LoanTool {

    private final LoanService loanService;

    public LoanTool(LoanService loanService) {
        this.loanService = loanService;
    }

    @Tool(
            name = "getLoanDetails",
            description = """
                    Retrieve loan details using the loan ID.

                    Use this tool when the user asks about:
                    - loan status
                    - customer ID associated with a loan
                    - pending loan process

                    If the loan does not exist, return the
                    appropriate error information.
                    """
    )
    public LoanToolResponse getLoanDetails(@ToolParam(description = "Unique loan ID") Long loanId) {
        try {
            log.info("Loan Tool -> Fetching loan details for loanId: {}", loanId);
            Loan loan = loanService.getLoan(loanId);
            return new LoanToolResponse(
                    true,
                    loan.loanId(),
                    loan.customerId(),
                    loan.status(),
                    loan.pendingProcess(),
                    null,
                    null
            );

        } catch (LoanNotFoundException exception) {
            log.error("Loan Tool -> Loan not found for loanId: {}", loanId);
            return new LoanToolResponse(
                    false,
                    loanId,
                    null,
                    null,
                    null,
                    "LOAN_NOT_FOUND",
                    "No loan was found for loan ID " + loanId
            );
        }
    }
}

package com.example.loanagent.tool;

import com.example.loanagent.income.IncomeDetails;

import com.example.loanagent.income.IncomeService;
import com.example.loanagent.tool.dto.IncomeToolResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IncomeTool {

    private final IncomeService incomeService;

    public IncomeTool(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Tool(
            name = "getIncomeDetails",
            description = """
                    Retrieve income verification details for a loan.

                    Use this tool when you need:
                    - monthly income
                    - income verification status
                    - income verification failure reason
                    """
    )
    public IncomeToolResponse getIncomeDetails(@ToolParam(description = "Unique loan ID") Long loanId) {
        log.info("Income Tool -> Fetching income details for loanId: {}", loanId);
        try {
            IncomeDetails income = incomeService.getIncomeDetails(loanId);
            return new IncomeToolResponse(
                    true,
                    income.loanId(),
                    income.monthlyIncome(),
                    income.verificationStatus(),
                    income.failureReason(),
                    null,
                    null
            );

        } catch (IllegalArgumentException exception) {
            log.error("Income Tool -> Income details not found for loanId: {}", loanId);
            return new IncomeToolResponse(
                    false,
                    loanId,
                    null,
                    null,
                    null,
                    "INCOME_NOT_FOUND",
                    exception.getMessage()
            );
        }
    }
}
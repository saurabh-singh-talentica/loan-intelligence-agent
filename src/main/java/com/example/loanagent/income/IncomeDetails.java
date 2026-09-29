package com.example.loanagent.income;

public record IncomeDetails(
        Long loanId,
        Long customerId,
        Double monthlyIncome,
        String verificationStatus,
        String failureReason
) {
}

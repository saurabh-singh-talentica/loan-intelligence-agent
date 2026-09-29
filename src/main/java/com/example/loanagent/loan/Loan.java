package com.example.loanagent.loan;

public record Loan(
        Long loanId,
        Long customerId,
        String status,
        String pendingProcess
) {
}
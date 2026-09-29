package com.example.loanagent.tool.dto;

public record LoanToolResponse(
        boolean success,
        Long loanId,
        Long customerId,
        String status,
        String pendingProcess,
        String errorCode,
        String message
) {
}
package com.example.loanagent.tool.dto;

public record IncomeToolResponse(
        boolean success,
        Long loanId,
        Double monthlyIncome,
        String verificationStatus,
        String failureReason,
        String errorCode,
        String message
) {
}
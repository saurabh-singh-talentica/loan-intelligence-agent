package com.example.loanagent.tool.dto;

public record CustomerToolResponse(
        boolean success,
        Long customerId,
        String name,
        String email,
        String errorCode,
        String message
) {
}